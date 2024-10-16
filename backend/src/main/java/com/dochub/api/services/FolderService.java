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
import com.dochub.api.repositories.FolderRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
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

    public Integer create (final UserRoleResponseDTO userRoles, final Group group,
                           final CreateFolderDTO createFolderDTO, final Folder parentFolder) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_FOLDER_PERMISSION);

        final Resource resource = new Resource(createFolderDTO, group, userRoles.user().username());
        final Folder folder = new Folder(parentFolder, userRoles.user().username());

        resource.setFolder(folder);
        folder.setResource(resource);

        return folderRepository.save(folder).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer folderId, final UpdateFolderDTO updateFolderDTO,
                        final Folder parentFolder) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.EDIT_FOLDER_PERMISSION);

        Utils.updateFieldIfPresent(updateFolderDTO.name(), folder.getResource()::setName);
        Utils.updateFieldIfPresent(updateFolderDTO.description(), folder.getResource()::setDescription);
        _updateParentFolderIfPresent(parentFolder, folder);

        _logAuditForChange(userRoles.user().username(), folder);

        folderRepository.save(folder);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer folderId,
                        final Function<Resource, List<ResourceRolePermission>> getAllByResourceFunc,
                        final BiConsumer<UserRoleResponseDTO, List<ResourceRolePermission>> deleteResourceRolePermissionsFunc) {
        final Folder folder = getById(folderId);

        Utils.checkPermission(userRoles, folder.getResource().getGroup().getId(), folderId, Constants.DELETE_FOLDER_PERMISSION);

        _deleteResourceRolePermissionsIfPresent(folder.getResource(), getAllByResourceFunc, userRoles, deleteResourceRolePermissionsFunc);

        folderRepository.delete(folder);
    }

    private void _updateParentFolderIfPresent (final Folder parentFolder, final Folder folder) {
        if (Objects.nonNull(parentFolder)) {
            folder.setParentFolder(parentFolder);

            return;
        }

        folder.setParentFolder(null);
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

    private void _logAuditForChange (final String actor, final Folder folder) {
        folder.getResource().getAuditRecord().setAlterationUser(actor);
        folder.getResource().getAuditRecord().setAlterationDate(new Date());

        folder.getAuditRecord().setAlterationUser(actor);
        folder.getAuditRecord().setAlterationDate(new Date());
    }
}