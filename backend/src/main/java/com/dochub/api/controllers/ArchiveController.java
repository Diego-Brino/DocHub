package com.dochub.api.controllers;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.archive.CreateArchiveDTO;
import com.dochub.api.dtos.archive.UpdateArchiveDTO;
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
@RequestMapping("/archives")
@RequiredArgsConstructor
public class ArchiveController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final GroupService groupService;
    private final FolderService folderService;
    private final ArchiveService archiveService;
    private final ResourceRolePermissionService resourceRolePermissionService;

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveResponseDTO> getOne (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                      @PathVariable("id") @NonNull final Integer archiveId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        return ResponseEntity
            .ok()
            .body(archiveService.getDtoById(user, archiveId));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @ModelAttribute @Valid @NonNull final CreateArchiveDTO createArchiveDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(createArchiveDTO.groupId());
        final Folder folder = Objects.nonNull(createArchiveDTO.folderId()) ? folderService.getById(createArchiveDTO.folderId()) : null;

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(archiveService.create(userRoles, group, createArchiveDTO, folder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer archiveId,
                                        @ModelAttribute @Valid @NonNull final UpdateArchiveDTO updateArchiveDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        archiveService.update(userRoles, archiveId, updateArchiveDTO, folderService::getById);

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer archiveId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        archiveService.delete(userRoles, archiveId, resourceRolePermissionService::getAllByResource, resourceRolePermissionService::delete);

        return ResponseEntity
            .noContent()
            .build();
    }
}