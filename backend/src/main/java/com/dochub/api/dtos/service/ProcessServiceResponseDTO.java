package com.dochub.api.dtos.service;

import com.dochub.api.entities.Service;

public record ProcessServiceResponseDTO (
    Integer id,
    String description
) {
    public ProcessServiceResponseDTO (final Service service) {
        this (
            service.getId(),
            service.getDescription()
        );
    }
}