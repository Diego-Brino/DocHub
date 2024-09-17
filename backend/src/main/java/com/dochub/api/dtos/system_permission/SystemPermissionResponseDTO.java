package com.dochub.api.dtos.system_permission;

import com.dochub.api.entities.SystemPermission;

public record SystemPermissionResponseDTO (
    Integer id,
    String description
) {
    public SystemPermissionResponseDTO (final SystemPermission systemPermission) {
        this (
            systemPermission.getId(),
            systemPermission.getDescription()
        );
    }
}