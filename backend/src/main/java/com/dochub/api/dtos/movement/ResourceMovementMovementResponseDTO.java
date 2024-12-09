package com.dochub.api.dtos.movement;

import com.dochub.api.dtos.request.MovementRequestResponseDTO;
import com.dochub.api.dtos.response_flow.ResponseFlowResponseDTO;
import com.dochub.api.entities.Movement;

public record ResourceMovementMovementResponseDTO (
    Integer id,
    MovementRequestResponseDTO request,
    ResponseFlowResponseDTO responseFlow,
    Integer order
) {
    public ResourceMovementMovementResponseDTO (final Movement movement) {
        this (
            movement.getId(),
            new MovementRequestResponseDTO(movement.getRequest()),
            new ResponseFlowResponseDTO(movement.getResponseFlow()),
            movement.getOrder()
        );
    }
}