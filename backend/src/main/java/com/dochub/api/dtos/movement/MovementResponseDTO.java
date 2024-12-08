package com.dochub.api.dtos.movement;

import com.dochub.api.dtos.request.MovementRequestResponseDTO;
import com.dochub.api.dtos.resource_movement.ResourceMovementResponseDTO;
import com.dochub.api.dtos.response_flow.ResponseFlowResponseDTO;
import com.dochub.api.entities.Movement;

import java.util.List;
import java.util.stream.Collectors;

public record MovementResponseDTO (
    Integer id,
    MovementRequestResponseDTO request,
    ResponseFlowResponseDTO responseFlow,
    Integer order,
    List<ResourceMovementResponseDTO> resourceMovements
) {
    public MovementResponseDTO (final Movement movement) {
        this (
            movement.getId(),
            new MovementRequestResponseDTO(movement.getRequest()),
            new ResponseFlowResponseDTO(movement.getResponseFlow()),
            movement.getOrder(),
            movement
                .getResourceMovements()
                .stream()
                .map(ResourceMovementResponseDTO::new)
                .collect(Collectors.toList())
        );
    }
}