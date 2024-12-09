package com.dochub.api.controllers;

import com.dochub.api.dtos.archive.ArchivePresignedUrlResponseDTO;
import com.dochub.api.dtos.resource_movement.CreateResourceMovementDTO;
import com.dochub.api.dtos.resource_movement.ResourceMovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.User;
import com.dochub.api.entities.resource_movement.ResourceMovementPK;
import com.dochub.api.services.*;
import com.dochub.api.services.s3.ObjectService;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource-movements")
@RequiredArgsConstructor
public class ResourceMovementController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final MovementService movementService;
    private final ResourceMovementService resourceMovementService;
    private final ArchiveService archiveService;
    private final ObjectService objectService;
    private final ResourceHistoryService resourceHistoryService;

    @GetMapping
    public ResponseEntity<List<ResourceMovementResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(resourceMovementService.getAll());
    }

    @GetMapping("/{movementId}/{resourceId}")
    public ResponseEntity<ResourceMovementResponseDTO> getOne (@PathVariable("movementId") @NonNull final Integer movementId,
                                                               @PathVariable("resourceId") @NonNull final Integer resourceId) {
        return ResponseEntity
            .ok()
            .body(resourceMovementService.getDtoById(movementId, resourceId));
    }

    @GetMapping("/presigned-url/create")
    public ResponseEntity<ArchivePresignedUrlResponseDTO> getPresignedUrlToCreateResource (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                                                           @RequestParam("movementId") @NotNull final Integer movementId,
                                                                                           @RequestParam("contentType") @NotBlank final String contentType) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Movement movement = movementService.getById(movementId);

        return ResponseEntity
            .ok()
            .body(archiveService.getPresignedUrlForCreate(
                userRoles,
                movement,
                movementService::isUserAuthorized,
                objectService::generatePresignedUrl,
                contentType
            ));
    }

    @PostMapping
    public ResponseEntity<ResourceMovementPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                      @RequestBody @Valid @NonNull final CreateResourceMovementDTO createResourceMovementDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Movement movement = movementService.getById(createResourceMovementDTO.movementId());
        final Resource resource = archiveService.create(
            userRoles,
            movement,
            movementService::isUserAuthorized,
            objectService::doesObjectExist,
            createResourceMovementDTO,
            resourceHistoryService::logCreationResourceHistory
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resourceMovementService.create(
                userRoles,
                movementService::isUserAuthorized,
                movement,
                resource
            ));
    }
}