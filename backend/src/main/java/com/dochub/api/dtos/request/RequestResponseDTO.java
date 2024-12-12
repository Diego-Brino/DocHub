package com.dochub.api.dtos.request;

import com.dochub.api.dtos.movement.MovementResponseDTO;
import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entities.Request;
import com.dochub.api.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public record RequestResponseDTO (
    Integer id,
    UserResponseDTO user,
    ProcessResponseDTO process,
    String status,
    List<MovementResponseDTO> movements,
    String startDate
) {
    public RequestResponseDTO (final Request request) {
        this (
            request.getId(),
            new UserResponseDTO(request.getUser()),
            new ProcessResponseDTO(request.getProcess()),
            request.getStatus().getCode(),
            request.getMovements()
                .stream()
                .map(MovementResponseDTO::new)
                .collect(Collectors.toList()),
            Utils.formatDate(request.getAuditRecord().getInsertionDate())
        );
    }
}
