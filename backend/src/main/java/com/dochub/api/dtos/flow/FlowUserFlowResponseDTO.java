package com.dochub.api.dtos.flow;

import com.dochub.api.dtos.activity.ActivityResponseDTO;
import com.dochub.api.dtos.process.FlowProcessResponseDTO;
import com.dochub.api.dtos.response_flow.ResponseFlowResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public record FlowUserFlowResponseDTO (
    Integer id,
    Integer order,
    Integer time,
    String limitDate,
    FlowProcessResponseDTO process,
    ActivityResponseDTO activity,
    List<ResponseFlowResponseDTO> responseFlows
) {
    public FlowUserFlowResponseDTO (final Flow flow) {
        this (
            flow.getId(),
            flow.getOrder(),
            flow.getTime(),
            Utils.formatDate(flow.getLimitDate()),
            new FlowProcessResponseDTO(flow.getProcess()),
            new ActivityResponseDTO(flow.getActivity()),
            flow.getResponseFlows()
                .stream()
                .map(ResponseFlowResponseDTO::new)
                .collect(Collectors.toList())
        );
    }
}