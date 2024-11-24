package com.dochub.api.dtos.flow_user;

import lombok.NonNull;

public record CreateFlowUserDTO(
    @NonNull
    Integer userId,

    @NonNull
    Integer flowId
) {
}