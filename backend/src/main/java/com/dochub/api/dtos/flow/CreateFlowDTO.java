package com.dochub.api.dtos.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;

import java.util.Date;

public record CreateFlowDTO (
    @NonNull
    Integer order,

    Integer time,

    @JsonFormat(pattern = "dd/MM/yyyy")
    Date limitDate,

    @NonNull
    Integer processId,

    @NonNull
    Integer activityId
) {
}