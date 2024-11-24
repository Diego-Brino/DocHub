package com.dochub.api.dtos.movement;

import lombok.NonNull;

public record CreateMovementDTO (
    @NonNull
    Integer requestId,

    @NonNull
    Integer flowId,

    @NonNull
    Integer responseId
) {
}
