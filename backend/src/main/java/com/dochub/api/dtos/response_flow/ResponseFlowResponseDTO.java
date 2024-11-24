package com.dochub.api.dtos.response_flow;

import com.dochub.api.dtos.flow.ResponseFlowFlowResponseDTO;
import com.dochub.api.dtos.response.ResponseResponseDTO;
import com.dochub.api.entities.response_flow.ResponseFlow;

public record ResponseFlowResponseDTO (
    ResponseFlowFlowResponseDTO flow,
    ResponseResponseDTO response,
    ResponseFlowFlowResponseDTO destinationFlow
) {
    public ResponseFlowResponseDTO (final ResponseFlow responseFlow) {
        this (
            new ResponseFlowFlowResponseDTO(responseFlow.getFlow()),
            new ResponseResponseDTO(responseFlow.getResponse()),
            new ResponseFlowFlowResponseDTO(responseFlow.getDestinationFlow())
        );
    }
}