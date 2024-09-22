package com.dochub.api.dtos.group_permission;

import com.dochub.api.entities.GroupPermission;

public record GroupPermissionResponseDTO (
    Integer id,
    String description
) {
    public GroupPermissionResponseDTO (final GroupPermission groupPermission) {
        this (
            groupPermission.getId(),
            groupPermission.getDescription()
        );
    }
}