package com.dochub.api.dtos.folder;

import com.dochub.api.entities.Resource;

public record FolderResponseDTO (
    Integer id,
    String name,
    String description
) {
    public FolderResponseDTO (final Resource resource) {
        this (
            resource.getId(),
            resource.getName(),
            resource.getDescription()
        );
    }
}