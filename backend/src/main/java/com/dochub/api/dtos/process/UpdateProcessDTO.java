package com.dochub.api.dtos.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record UpdateProcessDTO (
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date endDate,

    Integer serviceId,
    Integer groupId
) {
}