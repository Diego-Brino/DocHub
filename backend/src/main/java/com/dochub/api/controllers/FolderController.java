package com.dochub.api.controllers;

import com.dochub.api.dtos.folder.CreateFolderDTO;
import com.dochub.api.dtos.folder.UpdateFolderDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final GroupService groupService;
    private final FolderService folderService;
    private final ResourceRolePermissionService resourceRolePermissionService;

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateFolderDTO createFolderDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(createFolderDTO.groupId());
        final Folder parentFolder = Objects.nonNull(createFolderDTO.parentFolderId()) ? folderService.getById(createFolderDTO.parentFolderId()) : null;

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(folderService.create(userRoles, group, createFolderDTO, parentFolder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id,
                                        @RequestBody @Valid @NonNull final UpdateFolderDTO updateFolderDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Folder parentFolder = Objects.nonNull(updateFolderDTO.parentFolderId()) ? folderService.getById(updateFolderDTO.parentFolderId()) : null;

        folderService.update(userRoles, id, updateFolderDTO, parentFolder);

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer id) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        folderService.delete(userRoles, id, resourceRolePermissionService::getAllByResource, resourceRolePermissionService::delete);

        return ResponseEntity
            .noContent()
            .build();
    }
}
