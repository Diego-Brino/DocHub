package com.dochub.api.dtos.process;

import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.service.ProcessServiceResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public record ProcessResponseDTO (
    Integer id,
    String startDate,
    String endDate,
    ProcessServiceResponseDTO service,
    GroupResponseDTO group,
    List<FlowResponseDTO> flows
) {
    public ProcessResponseDTO (final Process process) {
        this (
            process.getId(),
            Utils.formatDate(process.getStartDate()),
            Utils.formatDate(process.getEndDate()),
            new ProcessServiceResponseDTO(process.getService()),
            new GroupResponseDTO(process.getGroup()),
            process
                .getFlows()
                .stream()
                .map(FlowResponseDTO::new)
                .collect(Collectors.toList())
        );
    }
}