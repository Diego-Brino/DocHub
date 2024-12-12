package com.dochub.api.dtos.resource_movement;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.movement.ResourceMovementMovementResponseDTO;
import com.dochub.api.entities.resource_movement.ResourceMovement;

public record ResourceMovementResponseDTO (
    ResourceMovementMovementResponseDTO movement,
    ArchiveResponseDTO archive
) {
    public ResourceMovementResponseDTO (final ResourceMovement resourceMovement) {
        this (
            new ResourceMovementMovementResponseDTO(resourceMovement.getMovement()),
            new ArchiveResponseDTO(resourceMovement.getResource())
        );
    }
}