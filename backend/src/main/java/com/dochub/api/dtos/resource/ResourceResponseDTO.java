package com.dochub.api.dtos.resource;

import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.entities.Resource;
import com.dochub.api.enums.ResourceType;

import java.util.Objects;

public record ResourceResponseDTO (
    Integer id,
    String name,
    String description,
    GroupResponseDTO group,
    ResourceType type
) {
    public ResourceResponseDTO (final Resource resource) {
        this (
            resource.getId(),
            resource.getName(),
            resource.getDescription(),
            new GroupResponseDTO(resource.getGroup()),
            Objects.nonNull(resource.getArchive()) ? ResourceType.ARCHIVE : ResourceType.FOLDER
        );
    }
}