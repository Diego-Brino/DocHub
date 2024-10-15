package com.dochub.api.dtos.resource_permission;

import com.dochub.api.entities.ResourcePermission;

public record ResourcePermissionResponseDTO (
    Integer id,
    String description
) {
    public ResourcePermissionResponseDTO (final ResourcePermission resourcePermission) {
        this (
            resourcePermission.getId(),
            resourcePermission.getDescription()
        );
    }
}