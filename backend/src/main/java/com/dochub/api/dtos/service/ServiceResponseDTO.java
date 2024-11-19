package com.dochub.api.dtos.service;

import com.dochub.api.entities.Service;

public record ServiceResponseDTO (
    Integer id,
    String description
) {
    public ServiceResponseDTO (final Service service) {
        this (
            service.getId(),
            service.getDescription()
        );
    }
}