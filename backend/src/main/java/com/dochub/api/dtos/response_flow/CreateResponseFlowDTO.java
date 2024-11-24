package com.dochub.api.dtos.response_flow;

import lombok.NonNull;

public record CreateResponseFlowDTO (
    @NonNull
    Integer flowId,

    @NonNull
    Integer responseId,

    Integer destinationFlowId
) {
}