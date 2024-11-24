package com.dochub.api.dtos.resource_movement;

import com.dochub.api.dtos.movement.MovementResponseDTO;
import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.entities.resource_movement.ResourceMovement;

public record ResourceMovementResponseDTO (
    MovementResponseDTO movement,
    ResourceResponseDTO resource
) {
    public ResourceMovementResponseDTO (final ResourceMovement resourceMovement) {
        this (
            new MovementResponseDTO(resourceMovement.getMovement()),
            new ResourceResponseDTO(resourceMovement.getResource())
        );
    }
}