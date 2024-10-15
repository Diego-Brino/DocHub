package com.dochub.api.services;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.archive.CreateArchiveDTO;
import com.dochub.api.dtos.archive.UpdateArchiveDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.*;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ArchiveRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
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

    public List<ArchiveResponseDTO> getGroupArchivesByFolder (final Group group, final Folder parentFolder, final User user) {
        final List<Archive> archives = Objects.isNull(parentFolder)
            ? archiveRepository.findByResource_GroupAndFolderIsNullWithPermission(group, user).orElse(Collections.emptyList())
            : archiveRepository.findByResource_GroupAndFolderWithPermission(group, parentFolder, user).orElse(Collections.emptyList());

        return archives
            .stream()
            .map(archive -> new ArchiveResponseDTO(archive.getResource()))
            .toList();
    }

    public Integer create (final UserRoleResponseDTO userRoles, final Group group,
                           final CreateArchiveDTO createArchiveDTO, final Folder folder) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_ARCHIVE_PERMISSION);

        final Resource resource = new Resource(createArchiveDTO, group, userRoles.user().username());
        final Archive archive = new Archive(createArchiveDTO, folder, userRoles.user().username());

        resource.setArchive(archive);
        archive.setResource(resource);

        return archiveRepository.save(archive).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer archiveId, final UpdateArchiveDTO updateArchiveDTO,
                        final Function<Integer, Folder> getFolderByIdFunc) {
        final Archive archive = getById(archiveId);

        Utils.checkPermission(userRoles, archive.getResource().getGroup().getId(), archiveId, Constants.EDIT_ARCHIVE_PERMISSION);

        Utils.updateFieldIfPresent(updateArchiveDTO.name(), archive.getResource()::setName);
        Utils.updateFieldIfPresent(updateArchiveDTO.description(), archive.getResource()::setDescription);
        _updateFileIfPresent(updateArchiveDTO.file(), archive);
        _updateFolderIfPresent(updateArchiveDTO.folderId(), getFolderByIdFunc, archive);

        _logAuditForChange(userRoles.user().username(), archive);

        archiveRepository.save(archive);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer archiveId,
                        final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                        final BiConsumer<UserRoleResponseDTO, List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final Archive archive = getById(archiveId);

        Utils.checkPermission(userRoles, archive.getResource().getGroup().getId(), archiveId, Constants.DELETE_ARCHIVE_PERMISSION);

        _deleteResourceRolePermissionsIfPresent(archive.getResource(), getAllByResourceFunc, userRoles, deleteResourceRolePermissionsFunc);

        archiveRepository.delete(archive);
    }

    private void _deleteResourceRolePermissionsIfPresent (final Resource resource,
                                                          final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                                                          final UserRoleResponseDTO userRoles,
                                                          final BiConsumer<UserRoleResponseDTO, List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final List<ResourceRolePermission> resourceRolePermissions = getAllByResourceFunc.apply(resource);

        if (!resourceRolePermissions.isEmpty()) {
            deleteResourceRolePermissionsFunc.accept(userRoles, resourceRolePermissions);
        }
    }

    private void _updateFileIfPresent (final MultipartFile file, final Archive archive) {
        if (Objects.nonNull(file)) {
            archive.setType(file.getContentType());
            archive.setLength(file.getSize());
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