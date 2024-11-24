package com.dochub.api.dtos.process;

import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.service.ProcessServiceResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.utils.Utils;

public record FlowProcessResponseDTO (
    Integer id,
    String startDate,
    String endDate,
    ProcessServiceResponseDTO service,
    GroupResponseDTO group
) {
    public FlowProcessResponseDTO (final Process process) {
        this (
            process.getId(),
            Utils.formatDate(process.getStartDate()),
            Utils.formatDate(process.getEndDate()),
            new ProcessServiceResponseDTO(process.getService()),
            new GroupResponseDTO(process.getGroup())
        );
    }
}