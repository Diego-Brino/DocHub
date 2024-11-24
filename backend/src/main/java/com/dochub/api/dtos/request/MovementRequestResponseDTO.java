package com.dochub.api.dtos.request;

import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entities.Request;

public record MovementRequestResponseDTO (
    Integer id,
    UserResponseDTO user,
    ProcessResponseDTO process,
    String status
) {
    public MovementRequestResponseDTO (final Request request) {
        this (
            request.getId(),
            new UserResponseDTO(request.getUser()),
            new ProcessResponseDTO(request.getProcess()),
            request.getStatus().getCode()
        );
    }
}
