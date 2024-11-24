package com.dochub.api.dtos.request;

import lombok.NonNull;

public record CreateRequestDTO (
    @NonNull
    Integer userId,

    @NonNull
    Integer processId
) {
}