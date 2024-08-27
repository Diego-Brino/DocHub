package com.dochub.api.dtos.system_permission;

import com.dochub.api.entity.SystemPermission;

public record SystemPermissionResponseDTO (
    String description
) {
    public SystemPermissionResponseDTO (SystemPermission systemPermission) {
        this (
            systemPermission.getDescription()
        );
    }
}