package com.dochub.api.controllers;

import com.dochub.api.dtos.resource_movement.CreateResourceMovementDTO;
import com.dochub.api.dtos.resource_movement.ResourceMovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
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
    private final ProcessService processService;
    private final MovementService movementService;
    private final ResourceService resourceService;
    private final ResourceMovementService resourceMovementService;

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

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateResourceMovementDTO createResourceMovementDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Movement movement = movementService.getById(createResourceMovementDTO.movementId());
        final Resource resource = resourceService.getById(createResourceMovementDTO.resourceId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resourceMovementService.create(
                userRoles,
                processService::isProcessFinished,
                movement,
                resource
            ));
    }
}