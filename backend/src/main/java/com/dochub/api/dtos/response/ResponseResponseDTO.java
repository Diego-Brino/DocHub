package com.dochub.api.dtos.response;

import com.dochub.api.entities.Response;

public record ResponseResponseDTO (
    Integer id,
    String description
) {
    public ResponseResponseDTO (final Response response) {
        this (
            response.getId(),
            response.getDescription()
        );
    }
}