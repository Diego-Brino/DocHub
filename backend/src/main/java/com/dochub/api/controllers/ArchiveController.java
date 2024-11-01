package com.dochub.api.controllers;

import com.dochub.api.dtos.archive.*;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.services.s3.ObjectService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ObjectService objectService;

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveResponseDTO> getOne (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                      @PathVariable("id") @NonNull final Integer archiveId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        return ResponseEntity
            .ok()
            .body(archiveService.getDtoById(user, archiveId));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getFile (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @PathVariable("id") @NonNull final Integer archiveId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);

        final ArchiveS3ResponseDTO archiveS3ResponseDTO = archiveService.getFile(user, archiveId, objectService::getObject);

        final String mediaType = MediaType.parseMediaType(archiveS3ResponseDTO.contentType()).toString();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, mediaType)
            .body(archiveS3ResponseDTO.file());
    }

    @GetMapping("/presigned-url/create")
    public ResponseEntity<ArchivePresignedUrlResponseDTO> getPresignedUrlToCreate (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                                   @RequestParam("groupId") @NotNull final Integer groupId) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(groupId);

        return ResponseEntity
            .ok()
            .body(archiveService.getPresignedUrlForCreate(
                userRoles,
                group,
                objectService::generatePresignedUrl
            ));
    }

    @GetMapping("/presigned-url/update")
    public ResponseEntity<ArchivePresignedUrlResponseDTO> getPresignedUrlToUpdate (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                                   @RequestParam("groupId") @NotNull final Integer groupId,
                                                                                   @RequestParam("hashS3") @NotBlank final String hashS3) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(groupId);

        return ResponseEntity
                .ok()
                .body(archiveService.getPresignedUrlForUpdate(
                        userRoles,
                        group,
                        hashS3,
                        objectService::generatePresignedUrl
                ));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateArchiveDTO createArchiveDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Group group = groupService.getById(createArchiveDTO.groupId());
        final Folder folder = Objects.nonNull(createArchiveDTO.folderId()) ? folderService.getById(createArchiveDTO.folderId()) : null;

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(archiveService.create(userRoles, group, objectService::doesObjectExist, createArchiveDTO, folder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("id") @NonNull final Integer archiveId,
                                        @RequestBody @Valid @NonNull final UpdateArchiveDTO updateArchiveDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        archiveService.update(userRoles, archiveId, objectService::doesObjectExist, updateArchiveDTO, folderService::getById);

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

        archiveService.delete(
            userRoles,
            archiveId,
            objectService::delete,
            resourceRolePermissionService::getAllByResource,
            resourceRolePermissionService::delete
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}