package com.dochub.api.dtos.process;

import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.service.ServiceResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.utils.Utils;

public record ProcessResponseDTO (
    Integer id,
    String startDate,
    String endDate,
    ServiceResponseDTO service,
    GroupResponseDTO group
) {
    public ProcessResponseDTO (final Process process) {
        this (
            process.getId(),
            Utils.formatDate(process.getStartDate()),
            Utils.formatDate(process.getEndDate()),
            new ServiceResponseDTO(process.getService()),
            new GroupResponseDTO(process.getGroup())
        );
    }
}