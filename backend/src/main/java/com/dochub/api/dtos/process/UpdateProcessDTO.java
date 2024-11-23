package com.dochub.api.dtos.process;

import java.util.Date;

public record UpdateProcessDTO (
    Date endDate,
    Integer serviceId,
    Integer groupId
) {
}