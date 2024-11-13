package com.dochub.api.services;

import com.dochub.api.dtos.archive.*;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.*;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.exceptions.ArchiveAlreadyExistsException;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.s3.ObjectNotFoundException;
import com.dochub.api.repositories.ArchiveRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.S3Utils;
import com.dochub.api.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ArchiveService {
    private final ArchiveRepository archiveRepository;

    public Archive getById (final Integer archiveId) {
        return archiveRepository
            .findById(archiveId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public Archive getById (final User user, final Integer archiveId) {
        return archiveRepository
            .findById(user, archiveId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ArchiveResponseDTO getDtoById (final User user, final Integer archiveId) {
        final Archive archive = getById(user, archiveId);

        return new ArchiveResponseDTO(archive.getResource());
    }

    public List<Archive> getGroupArchivesByGroup (final Group group) {
        return archiveRepository
            .findByResource_Group(group)
            .orElse(Collections.emptyList());
    }

    public List<ArchiveResponseDTO> getGroupArchivesByFolder (final Group group, final Folder parentFolder, final User user) {
        final List<Archive> archives = Objects.isNull(parentFolder)
            ? archiveRepository.findByResource_GroupAndFolderIsNullWithPermission(group, user).orElse(Collections.emptyList())
            : archiveRepository.findByResource_GroupAndFolderWithPermission(group, parentFolder, user).orElse(Collections.emptyList());

        return archives
            .stream()
            .map(archive -> new ArchiveResponseDTO(archive.getResource()))
            .toList();
    }

    public ArchiveS3ResponseDTO getFile (final User user, final Integer archiveId,
                                         final BiFunction<String, String, ArchiveS3ResponseDTO> getObjectFunc) {
        final Archive archive = getById(user, archiveId);

        return getObjectFunc.apply(archive.getResource().getGroup().getIdS3Bucket(), archive.getS3Hash());
    }

    public ArchivePresignedUrlResponseDTO getPresignedUrlForCreate (final UserRoleResponseDTO userRoles, final Group group,
                                                                    final TriFunction<String, String, String, String> generatePresignedUrlFunc,
                                                                    final String contentType) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_ARCHIVE_PERMISSION);

        final String fileName = S3Utils.generateFileName();
        final String presignedUrl = generatePresignedUrlFunc.apply(group.getIdS3Bucket(), fileName, contentType);

        return new ArchivePresignedUrlResponseDTO(presignedUrl, fileName);
    }

    public ArchivePresignedUrlResponseDTO getPresignedUrlForUpdate (final UserRoleResponseDTO userRoles, final Group group, final String fileName,
                                                                    final TriFunction<String, String, String, String> generatePresignedUrlFunc,
                                                                    final String contentType) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_ARCHIVE_PERMISSION);

        final String presignedUrl = generatePresignedUrlFunc.apply(group.getIdS3Bucket(), fileName, contentType);

        return new ArchivePresignedUrlResponseDTO(presignedUrl, fileName);
    }

    @Transactional
    public Integer create (final UserRoleResponseDTO userRoles, final Group group,
                           final BiFunction<String, String, Boolean> doesObjectExistsFunc,
                           final CreateArchiveDTO createArchiveDTO, final Folder folder,
                           final TriConsumer<Group, String, String> logCreationResourceHistoryFunc) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_ARCHIVE_PERMISSION);

        if (!doesObjectExistsFunc.apply(group.getIdS3Bucket(), createArchiveDTO.hashS3())) {
            throw new ObjectNotFoundException(group.getIdS3Bucket(), createArchiveDTO.hashS3());
        }

        if (archiveRepository.findByS3Hash(createArchiveDTO.hashS3()).isPresent()) {
            throw new ArchiveAlreadyExistsException();
        }

        final Resource resource = new Resource(createArchiveDTO, group, userRoles.user().username());
        final Archive archive = new Archive(createArchiveDTO, folder, userRoles.user().username());

        resource.setArchive(archive);
        archive.setResource(resource);

        _logCreationHistory(resource.getName(), folder, group, userRoles.user().username(), logCreationResourceHistoryFunc);

        return archiveRepository.save(archive).getId();
    }

    @Transactional
    public void update (final UserRoleResponseDTO userRoles,
                        final Integer archiveId,
                        final BiFunction<String, String, Boolean> doesObjectExistsFunc,
                        final UpdateArchiveDTO updateArchiveDTO,
                        final Function<Integer, Folder> getFolderByIdFunc,
                        final TriConsumer<Group, String, String> logEditResourceHistoryFunc) {
        final Archive archive = getById(archiveId);

        if (!doesObjectExistsFunc.apply(archive.getResource().getGroup().getIdS3Bucket(), archive.getS3Hash())) {
            throw new ObjectNotFoundException(archive.getResource().getGroup().getIdS3Bucket(), archive.getS3Hash());
        }

        Utils.checkPermission(userRoles, archive.getResource().getGroup().getId(), archiveId, Constants.EDIT_ARCHIVE_PERMISSION);

        _logEditHistory(archive, updateArchiveDTO, getFolderByIdFunc, userRoles.user().username(), logEditResourceHistoryFunc);

        Utils.updateFieldIfPresent(updateArchiveDTO.name(), archive.getResource()::setName);
        Utils.updateFieldIfPresent(updateArchiveDTO.description(), archive.getResource()::setDescription);
        Utils.updateFieldIfPresent(updateArchiveDTO.contentType(), archive::setType);
        _updateLengthIfPresent(updateArchiveDTO.length(), archive);
        _updateFolderIfPresent(updateArchiveDTO.folderId(), getFolderByIdFunc, archive);

        _logAuditForChange(userRoles.user().username(), archive);

        archiveRepository.save(archive);
    }

    @Transactional
    public void delete (final UserRoleResponseDTO userRoles, final Integer archiveId,
                        final TriConsumer<Group, String, String> logDeletionResourceHistoryFunc,
                        final BiConsumer<String, String> deleteS3ObjectFunc,
                        final Function<Resource, List<ResourceRolePermission>> getAllResourceRolePermissionsByResourceFunc,
                        final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final Archive archive = getById(archiveId);

        Utils.checkPermission(userRoles, archive.getResource().getGroup().getId(), archiveId, Constants.DELETE_ARCHIVE_PERMISSION);

        _logDeletionHistory(archive, userRoles.user().username(), logDeletionResourceHistoryFunc);

        deleteS3ObjectFunc.accept(archive.getResource().getGroup().getIdS3Bucket(), archive.getS3Hash());

        _deleteResourceRolePermissionsIfPresent(archive.getResource(), getAllResourceRolePermissionsByResourceFunc, deleteResourceRolePermissionsFunc);

        archiveRepository.delete(archive);
    }

    @Transactional
    public void deleteAllArchivesAssignedToGroup (final Group group,
                                                  final Function<Resource, List<ResourceRolePermission>> getAllResourceRolePermissionsByResourceFunc,
                                                  final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final List<Archive> archives = archiveRepository
            .findByResource_Group(group)
            .orElse(Collections.emptyList());

        archives.forEach(archive -> _deleteResourceRolePermissionsIfPresent(archive.getResource(), getAllResourceRolePermissionsByResourceFunc, deleteResourceRolePermissionsFunc));

        archiveRepository.deleteAll(archives);
    }

    private void _logCreationHistory (final String archiveName, final Folder folder,
                                      final Group group, final String actionUser,
                                      final TriConsumer<Group, String, String> logCreationResourceHistoryFunc) {
        final String createdIn = Objects.nonNull(folder) ?
            folder.getResource().getName() :
            Constants.ROOT;

        logCreationResourceHistoryFunc.accept(
            group,
            String.format(Constants.RESOURCE_CREATED_HISTORY_MESSAGE, archiveName, createdIn),
            actionUser
        );
    }

    private void _logEditHistory (final Archive archive,
                                  final UpdateArchiveDTO updateArchiveDTO,
                                  final Function<Integer, Folder> getFolderByIdFunc,
                                  final String actionUser,
                                  final TriConsumer<Group, String, String> logEditResourceHistoryFunc) {
        final StringBuilder description = new StringBuilder();

        _appendUpdatedName(archive, updateArchiveDTO, description);
        _appendUpdatedDescription(archive, updateArchiveDTO, description);
        _appendUpdatedContentType(archive, updateArchiveDTO, description);
        _appendUpdatedLength(archive, updateArchiveDTO, description);
        _appendUpdatedFolder(archive, updateArchiveDTO, getFolderByIdFunc, description);

        logEditResourceHistoryFunc.accept(archive.getResource().getGroup(), description.toString(), actionUser);
    }

    private void _logDeletionHistory (final Archive archive,
                                      final String actionUser,
                                      final TriConsumer<Group, String, String> logDeletionResourceHistoryFunc) {
        final String resourceIn = Objects.nonNull(archive.getFolder()) ?
            archive.getFolder().getResource().getName() :
            Constants.ROOT;

        logDeletionResourceHistoryFunc.accept(
            archive.getResource().getGroup(),
            String.format(Constants.RESOURCE_DELETED_HISTORY_MESSAGE, archive.getResource().getName(), resourceIn),
            actionUser
        );
    }

    private void _appendUpdatedName (final Archive archive,
                                     final UpdateArchiveDTO updateArchiveDTO,
                                     final StringBuilder description) {
        if (Objects.nonNull(updateArchiveDTO.name()) && !updateArchiveDTO.name().isBlank()) {
            final String message = String.format(
                Constants.RESOURCE_NAME_UPDATED_MESSAGE,
                archive.getResource().getName(), updateArchiveDTO.name()
            );

            description.append(message).append("\n");
        }
    }

    private void _appendUpdatedDescription (final Archive archive,
                                            final UpdateArchiveDTO updateArchiveDTO,
                                            final StringBuilder description) {
        if (Objects.nonNull(updateArchiveDTO.description()) && !updateArchiveDTO.description().isBlank()) {
            final String message = String.format(
                Constants.RESOURCE_DESCRIPTION_UPDATED_MESSAGE,
                archive.getResource().getDescription(), updateArchiveDTO.description()
            );

            description.append(message).append("\n");
        }
    }

    private void _appendUpdatedContentType (final Archive archive,
                                            final UpdateArchiveDTO updateArchiveDTO,
                                            final StringBuilder description) {
        if (Objects.nonNull(updateArchiveDTO.contentType()) && !updateArchiveDTO.contentType().isBlank()) {
            final String message = String.format(
                Constants.RESOURCE_CONTENT_TYPE_UPDATED_MESSAGE,
                archive.getType(), updateArchiveDTO.contentType()
            );

            description.append(message).append("\n");
        }
    }

    private void _appendUpdatedLength (final Archive archive,
                                       final UpdateArchiveDTO updateArchiveDTO,
                                       final StringBuilder description) {
        if (Objects.nonNull(updateArchiveDTO.length())) {
            final String message = String.format(
                Constants.RESOURCE_LENGTH_UPDATED_MESSAGE,
                archive.getLength(), updateArchiveDTO.length()
            );

            description.append(message).append("\n");
        }
    }

    private void _appendUpdatedFolder (final Archive archive,
                                       final UpdateArchiveDTO updateArchiveDTO,
                                       final Function<Integer, Folder> getFolderByIdFunc,
                                       final StringBuilder description) {
        if (Objects.nonNull(updateArchiveDTO.folderId())) {
            final Folder newFolder = getFolderByIdFunc.apply(updateArchiveDTO.folderId());

            final String oldFolderName = archive.getFolder() != null ?
                archive.getFolder().getResource().getName() :
                Constants.ROOT;

            final String message = String.format(
                Constants.RESOURCE_FOLDER_UPDATED_MESSAGE,
                oldFolderName, newFolder.getResource().getName()
            );

            description.append(message).append("\n");
        }else if (Objects.nonNull(archive.getFolder())) {
            final String message = String.format(
                Constants.RESOURCE_FOLDER_UPDATED_MESSAGE,
                archive.getFolder().getResource().getName(), Constants.ROOT
            );

            description.append(message).append("\n");
        }
    }

    private void _deleteResourceRolePermissionsIfPresent (final Resource resource,
                                                          final Function<Resource, List<ResourceRolePermission>> getAllResourceRolePermissionsByResourceFunc,
                                                          final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final List<ResourceRolePermission> resourceRolePermissions = getAllResourceRolePermissionsByResourceFunc.apply(resource);

        if (!resourceRolePermissions.isEmpty()) {
            deleteResourceRolePermissionsFunc.accept(resourceRolePermissions);
        }
    }

    private void _updateLengthIfPresent (final Long length, final Archive archive) {
        if (Objects.nonNull(length)) {
            archive.setLength(length);
        }
    }

    private void _updateFolderIfPresent (final Integer folderId, final Function<Integer, Folder> getFolderByIdFunc,
                                         final Archive archive) {
        if (Objects.nonNull(folderId)) {
            final Folder folder = getFolderByIdFunc.apply(folderId);

            archive.setFolder(folder);

            return;
        }

        archive.setFolder(null);
    }

    private void _logAuditForChange (final String actor, final Archive archive) {
        archive.getResource().getAuditRecord().setAlterationUser(actor);
        archive.getResource().getAuditRecord().setAlterationDate(new Date());

        archive.getAuditRecord().setAlterationUser(actor);
        archive.getAuditRecord().setAlterationDate(new Date());
    }
}