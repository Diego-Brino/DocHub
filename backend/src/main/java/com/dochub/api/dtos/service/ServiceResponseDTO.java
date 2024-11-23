package com.dochub.api.dtos.service;

import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.entities.Service;

import java.util.List;
import java.util.stream.Collectors;

public record ServiceResponseDTO (
    Integer id,
    String description,
    List<ProcessResponseDTO> processes
) {
    public ServiceResponseDTO (final Service service) {
        this (
            service.getId(),
            service.getDescription(),
            service
                .getProcesses()
                .stream()
                .map(ProcessResponseDTO::new)
                .collect(Collectors.toList())
        );
    }
}