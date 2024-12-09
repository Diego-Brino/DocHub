package com.dochub.api.dtos.resource_movement;

import com.dochub.api.dtos.movement.ResourceMovementMovementResponseDTO;
import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.entities.resource_movement.ResourceMovement;

public record ResourceMovementResponseDTO (
    ResourceMovementMovementResponseDTO movement,
    ResourceResponseDTO resource
) {
    public ResourceMovementResponseDTO (final ResourceMovement resourceMovement) {
        this (
            new ResourceMovementMovementResponseDTO(resourceMovement.getMovement()),
            new ResourceResponseDTO(resourceMovement.getResource())
        );
    }
}