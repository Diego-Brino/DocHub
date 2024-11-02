package com.dochub.api.services;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.folder.CreateFolderDTO;
import com.dochub.api.dtos.folder.FolderContentsResponseDTO;
import com.dochub.api.dtos.folder.FolderResponseDTO;
import com.dochub.api.dtos.folder.UpdateFolderDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.User;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.InvalidFolderMoveException;
import com.dochub.api.repositories.FolderRepository;
import com.dochub.api.utils.Constants;
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
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public Folder getById (final Integer folderId) {
        return folderRepository
            .findById(folderId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public List<FolderResponseDTO> getGroupFoldersByParentFolder (final Group group, final Folder parentFolder, final User user) {
        final List<Folder> folders = Objects.isNull(parentFolder)
            ? folderRepository.findByResource_GroupAndParentFolderIsNullWithPermission(group, user).orElse(Collections.emptyList())
            : folderRepository.findByResource_GroupAndParentFolderWithPermission(group, parentFolder, user).orElse(Collections.emptyList());

        return folders
            .stream()
            .map(folder -> new FolderResponseDTO(folder.getResource()))
            .toList();
    }

    public FolderContentsResponseDTO getFolderContents (final Group group, final Folder parentFolder, final User user,
                                                        final TriFunction<Group, Folder, User, List<ArchiveResponseDTO>> getGroupArchivesByFolderFunc) {
        final List<ArchiveResponseDTO> archives = getGroupArchivesByFolderFunc.apply(group, parentFolder, user);
        final List<FolderResponseDTO> subFolders = getGroupFoldersByParentFolder(group, parentFolder, user);

        return new FolderContentsResponseDTO(archives, subFolders);
    }

    @Transactional
    public Integer create (final UserRoleResponseDTO userRoles, final Group group,
                           final CreateFolderDTO createFolderDTO, final Folder parentFolder,
                           final TriConsumer<Group, String, String> logCreationResourceHistoryFunc) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_FOLDER_PERMISSION);

        final Resource resource = new Resource(createFolderDTO, group, userRoles.user().username());
        final Folder folder = new Folder(parentFolder, userRoles.user().username());

        resource.setFolder(folder);
        folder.setResource(resource);

        _logCreationHistory(resource.getName(), parentFolder, group, userRoles.user().username(), logCreationResourceHistoryFunc);

        return folderRepository.save(folder).getId();
    }

    @Transactional
    public void update (final UserRoleResponseDTO userRoles,
                        final Integer folderId, final UpdateFolderDTO updateFolderDTO, final Folder parentFolder,
                        final TriConsumer<Group, String, String> logEditResourceHistoryFunc) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.EDIT_FOLDER_PERMISSION);

        _logEditHistory(folder, updateFolderDTO, parentFolder, userRoles.user().username(), logEditResourceHistoryFunc);

        Utils.updateFieldIfPresent(updateFolderDTO.name(), folder.getResource()::setName);
        Utils.updateFieldIfPresent(updateFolderDTO.description(), folder.getResource()::setDescription);
        _updateParentFolderIfPresent(parentFolder, folder);

        _logAuditForChange(userRoles.user().username(), folder);

        folderRepository.save(folder);
    }

    @Transactional
    public void delete (final UserRoleResponseDTO userRoles, final Integer folderId,
                        final TriConsumer<Group, String, String> logDeletionResourceHistoryFunc,
                        final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                        final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.DELETE_FOLDER_PERMISSION);

        _logDeletionHistory(folder, userRoles.user().username(), logDeletionResourceHistoryFunc);

        _deleteResourceRolePermissionsIfPresent(folder.getResource(), getAllByResourceFunc, deleteResourceRolePermissionsFunc);

        folderRepository.delete(folder);
    }

    @Transactional
    public void deleteAllFoldersAssignedToGroup (final Group group,
                                                 final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                                                 final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final List<Folder> folders = folderRepository
            .findByResource_Group(group)
            .orElse(Collections.emptyList());

        folders.forEach(archive -> _deleteResourceRolePermissionsIfPresent(archive.getResource(), getAllByResourceFunc, deleteResourceRolePermissionsFunc));

        folderRepository.deleteAll(folders);
    }

    private void _logCreationHistory (final String folderName, final Folder parentFolder,
                                      final Group group, final String actionUser,
                                      final TriConsumer<Group, String, String> logCreationResourceHistoryFunc) {
        final String createdIn = Objects.nonNull(parentFolder) ?
            parentFolder.getResource().getName() :
            Constants.ROOT;

        logCreationResourceHistoryFunc.accept(
            group,
            String.format(Constants.RESOURCE_CREATED_HISTORY_MESSAGE, folderName, createdIn),
            actionUser
        );
    }

    private void _logEditHistory (final Folder folder, final UpdateFolderDTO updateFolderDTO, final Folder parentFolder,
                                  final String actionUser,
                                  final TriConsumer<Group, String, String> logEditResourceHistoryFunc) {
        final StringBuilder description = new StringBuilder();

        _appendUpdatedName(folder, updateFolderDTO, description);
        _appendUpdatedDescription(folder, updateFolderDTO, description);
        _appendUpdatedFolderLocation(folder, parentFolder, description);

        logEditResourceHistoryFunc.accept(folder.getResource().getGroup(), description.toString(), actionUser);
    }

    private void _logDeletionHistory (final Folder folder,
                                      final String actionUser,
                                      final TriConsumer<Group, String, String> logDeletionResourceHistoryFunc) {
        final String resourceIn = Objects.nonNull(folder.getParentFolder()) ?
            folder.getParentFolder().getResource().getName() :
            Constants.ROOT;

        logDeletionResourceHistoryFunc.accept(
            folder.getResource().getGroup(),
            String.format(Constants.RESOURCE_DELETED_HISTORY_MESSAGE, folder.getResource().getName(), resourceIn),
            actionUser
        );
    }

    private void _appendUpdatedName (final Folder folder,
                                     final UpdateFolderDTO updateFolderDTO,
                                     final StringBuilder descriptionBuilder) {
        if (Objects.nonNull(updateFolderDTO.name()) && !updateFolderDTO.name().isBlank()) {
            final String message = String.format(
                Constants.RESOURCE_NAME_UPDATED_MESSAGE,
                folder.getResource().getName(), updateFolderDTO.name()
            );

            descriptionBuilder.append(message).append("\n");
        }
    }

    private void _appendUpdatedDescription (final Folder folder,
                                            final UpdateFolderDTO updateFolderDTO,
                                            final StringBuilder description) {
        if (Objects.nonNull(updateFolderDTO.description()) && !updateFolderDTO.description().isBlank()) {
            final String message = String.format(
                Constants.RESOURCE_DESCRIPTION_UPDATED_MESSAGE,
                folder.getResource().getDescription(), updateFolderDTO.description()
            );

            description.append(message).append("\n");
        }
    }

    private void _appendUpdatedFolderLocation (final Folder folder,
                                               final Folder parentFolder,
                                               final StringBuilder description) {
        if (Objects.nonNull(parentFolder)) {
            final String oldParentName = Objects.nonNull(folder.getParentFolder()) ?
                folder.getParentFolder().getResource().getName() :
                Constants.ROOT;

            final String newParentName = parentFolder.getResource().getName();

            final String message = String.format(
                Constants.RESOURCE_FOLDER_UPDATED_MESSAGE,
                oldParentName, newParentName
            );

            description.append(message).append("\n");
        }else if (Objects.nonNull(folder.getParentFolder())) {
            final String message = String.format(
                Constants.RESOURCE_FOLDER_UPDATED_MESSAGE,
                folder.getParentFolder().getResource().getName(), Constants.ROOT
            );

            description.append(message).append("\n");
        }
    }

    private void _updateParentFolderIfPresent (final Folder parentFolder, final Folder folder) {
        if (Objects.nonNull(parentFolder) && parentFolder.equals(folder)) {
            throw new InvalidFolderMoveException();
        }

        if (Objects.nonNull(parentFolder)) {
            folder.setParentFolder(parentFolder);

            return;
        }

        folder.setParentFolder(null);
    }

    private void _deleteResourceRolePermissionsIfPresent (final Resource resource,
                                                          final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                                                          final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final List<ResourceRolePermission> resourceRolePermissions = getAllByResourceFunc.apply(resource);

        if (!resourceRolePermissions.isEmpty()) {
            deleteResourceRolePermissionsFunc.accept(resourceRolePermissions);
        }
    }

    private void _logAuditForChange (final String actor, final Folder folder) {
        folder.getResource().getAuditRecord().setAlterationUser(actor);
        folder.getResource().getAuditRecord().setAlterationDate(new Date());

        folder.getAuditRecord().setAlterationUser(actor);
        folder.getAuditRecord().setAlterationDate(new Date());
    }
}