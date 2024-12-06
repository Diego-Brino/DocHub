package com.dochub.api.controllers;

import com.dochub.api.dtos.folder.FolderContentsResponseDTO;
import com.dochub.api.dtos.group.CreateGroupDTO;
import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.group.UpdateGroupDTO;
import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.dtos.resource.RootGroupResourcesResponseDTO;
import com.dochub.api.dtos.resource_history.ResourceHistoryResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.services.s3.BucketService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final GroupRolePermissionService groupRolePermissionService;
    private final ArchiveService archiveService;
    private final FolderService folderService;
    private final ResourceService resourceService;
    private final ProcessService processService;
    private final RoleService roleService;
    private final GroupPermissionService groupPermissionService;
    private final BucketService bucketService;
    private final ResourceRolePermissionService resourceRolePermissionService;
    private final ResourceHistoryService resourceHistoryService;

    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> getAll (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        return ResponseEntity
            .ok()
            .body(groupService.getGroupsByUserWithViewPermission(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getOne (@PathVariable("id") @NonNull final Integer groupId) {
        return ResponseEntity
            .ok()
            .body(groupService.getDtoById(groupId));
    }

    @GetMapping(path = "/{id}/avatar", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getAvatar (@PathVariable("id") @NonNull final Integer groupId) {
        return ResponseEntity
            .ok()
            .body(groupService.getGroupAvatar(groupId));
    }

    @GetMapping("/{id}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> getAllGroupResources (@PathVariable("id") @NonNull final Integer groupId) {
        final Group group = groupService.getById(groupId);

        return ResponseEntity
            .ok()
            .body(
                resourceService.getAllGroupResources(
                    group,
                    archiveService::getGroupArchivesByGroup,
                    folderService::getGroupFoldersByGroup
                )
            );
    }

    @GetMapping("/{id}/root-resources")
    public ResponseEntity<RootGroupResourcesResponseDTO> getGroupRootResources (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                                @PathVariable("id") @NonNull final Integer groupId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final Group group = groupService.getById(groupId);

        return ResponseEntity
            .ok()
            .body(
                resourceService.getRootGroupResources(
                    user,
                    group,
                    archiveService::getGroupArchivesByFolder,
                    folderService::getGroupFoldersByParentFolder
                )
            );
    }

    @GetMapping("/{groupId}/folder/{folderId}/contents")
    public ResponseEntity<FolderContentsResponseDTO> getFolderContents (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                        @PathVariable("groupId") @NonNull final Integer groupId,
                                                                        @PathVariable("folderId") @NonNull final Integer folderId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final Group group = groupService.getById(groupId);
        final Folder folder = folderService.getById(folderId);

        return ResponseEntity
            .ok()
            .body(
                folderService.getFolderContents(
                    group,
                    folder,
                    user,
                    archiveService::getGroupArchivesByFolder
                )
            );
    }

    @GetMapping("/{id}/resource-histories")
    public ResponseEntity<List<ResourceHistoryResponseDTO>> getAllResourceHistories (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                                     @PathVariable("id") @NonNull final Integer groupId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(groupId);

        return ResponseEntity
            .ok()
            .body(resourceHistoryService.getAllByGroup(userRoles, group));
    }

    @GetMapping("/{id}/process")
    public ResponseEntity<List<ProcessResponseDTO>> getAllProcess (@PathVariable("id") @NonNull final Integer groupId) {
        final Group group = groupService.getById(groupId);

        return ResponseEntity
            .ok()
            .body(processService.getAllByGroup(group));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @ModelAttribute @NonNull @Valid final CreateGroupDTO createGroupDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                groupService.create(
                    userRoles,
                    createGroupDTO,
                    bucketService::create,
                    roleService::getByName,
                    groupPermissionService::getByDescription,
                    groupRolePermissionService::assignViewPermissionToAdmin
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer groupId,
                                        @ModelAttribute @NonNull @Valid final UpdateGroupDTO updateGroupDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        groupService.update(userRoles, groupId, updateGroupDTO);

        return ResponseEntity
            .ok()
            .build();
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer groupId,
                                        @RequestPart(Constants.AVATAR_PARAMETER) @NonNull final MultipartFile groupAvatar) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        groupService.updateAvatar(userRoles, groupId, groupAvatar);

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer groupId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        groupService.delete(
            userRoles,
            groupId,
            bucketService::deleteBucketWithContentsAsync,
            groupRolePermissionService::deleteAllGroupRolePermissionsAssignedToGroup,
            resourceRolePermissionService::getAllByResource,
            resourceRolePermissionService::delete,
            archiveService::deleteAllArchivesAssignedToGroup,
            folderService::deleteAllFoldersAssignedToGroup,
            resourceHistoryService::deleteAllAssignedToGroup,
            processService::deleteAllProcessAssignedToGroup
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}