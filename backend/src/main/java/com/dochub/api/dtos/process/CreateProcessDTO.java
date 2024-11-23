package com.dochub.api.dtos.process;

import lombok.NonNull;

public record CreateProcessDTO (
    @NonNull
    Integer serviceId,

    @NonNull
    Integer groupId
) {
}