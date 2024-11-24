package com.dochub.api.dtos.resource_movement;

import lombok.NonNull;

public record CreateResourceMovementDTO (
    @NonNull
    Integer movementId,

    @NonNull
    Integer resourceId
) {
}
