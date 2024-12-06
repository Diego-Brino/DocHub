package com.dochub.api.dtos.role;

import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.entities.Role;

import java.util.List;

public record ResourceRoleResponseDTO (
    Integer id,
    String name,
    String description,
    String color,
    String status,
    List<ResourcePermissionResponseDTO> permissions
) {
    public ResourceRoleResponseDTO (final Role role, final List<ResourcePermissionResponseDTO> permissions) {
        this(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.getColor(),
            role.getRoleStatus().getCode(),
            permissions
        );
    }
}