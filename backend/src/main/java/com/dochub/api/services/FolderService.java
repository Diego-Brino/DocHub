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
import com.dochub.api.enums.ResourceHistoryActionType;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.InvalidFolderMoveException;
import com.dochub.api.repositories.FolderRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final ResourceHistoryService resourceHistoryService;

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
                           final CreateFolderDTO createFolderDTO, final Folder parentFolder) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_FOLDER_PERMISSION);

        final Resource resource = new Resource(createFolderDTO, group, userRoles.user().username());
        final Folder folder = new Folder(parentFolder, userRoles.user().username());

        resource.setFolder(folder);
        folder.setResource(resource);

        final Integer folderId = folderRepository.save(folder).getId();

        final String folderLocalDescription = Objects.nonNull(parentFolder) ?
            parentFolder.getResource().getName() :
            Constants.ROOT;

        resourceHistoryService.create(
            resource,
            parentFolder,
            ResourceHistoryActionType.CREATED,
            String.format(Constants.RESOURCE_CREATED_HISTORY_MESSAGE, resource.getName(), folderLocalDescription),
            userRoles.user().username()
        );

        return folderId;
    }

    @Transactional
    public void update (final UserRoleResponseDTO userRoles,
                        final Integer folderId, final UpdateFolderDTO updateFolderDTO,
                        final Folder parentFolder) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.EDIT_FOLDER_PERMISSION);

        resourceHistoryService.create(
            folder.getResource(),
            folder.getParentFolder(),
            Objects.nonNull(parentFolder) ? parentFolder : null,
            ResourceHistoryActionType.EDITED,
            userRoles.user().username()
        );

        Utils.updateFieldIfPresent(updateFolderDTO.name(), folder.getResource()::setName);
        Utils.updateFieldIfPresent(updateFolderDTO.description(), folder.getResource()::setDescription);
        _updateParentFolderIfPresent(parentFolder, folder);

        _logAuditForChange(userRoles.user().username(), folder);

        folderRepository.save(folder);
    }

    @Transactional
    public void delete (final UserRoleResponseDTO userRoles, final Integer folderId,
                        final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                        final Consumer<List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.DELETE_FOLDER_PERMISSION);

        resourceHistoryService.create(
            folder.getResource(),
            folder.getParentFolder(),
            folder.getParentFolder(),
            ResourceHistoryActionType.DELETED,
            userRoles.user().username()
        );

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

        folders.forEach(folder -> resourceHistoryService.create(
            folder.getResource(),
            folder.getParentFolder(),
            ResourceHistoryActionType.DELETED,
            String.format(Constants.RESOURCE_DELETED_BY_GROUP_DELETION_MESSAGE, group.getName()),
            Constants.SYSTEM_NAME
        ));

        folderRepository.deleteAll(folders);
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