package com.dochub.api.dtos.flow;

import com.dochub.api.dtos.activity.ActivityResponseDTO;
import com.dochub.api.dtos.process.FlowProcessResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.utils.Utils;

public record ResponseFlowFlowResponseDTO (
    Integer id,
    Integer order,
    Integer time,
    String limitDate,
    FlowProcessResponseDTO process,
    ActivityResponseDTO activity
) {
    public ResponseFlowFlowResponseDTO (final Flow flow) {
        this (
            flow.getId(),
            flow.getOrder(),
            flow.getTime(),
            Utils.formatDate(flow.getLimitDate()),
            new FlowProcessResponseDTO(flow.getProcess()),
            new ActivityResponseDTO(flow.getActivity())
        );
    }
}