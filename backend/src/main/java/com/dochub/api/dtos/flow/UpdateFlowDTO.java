package com.dochub.api.dtos.flow;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record UpdateFlowDTO (
    Integer order,
    Integer time,

    @JsonFormat(pattern = "dd/MM/yyyy")
    Date limitDate,

    Integer activityId
) {
}