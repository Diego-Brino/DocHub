package com.dochub.api.dtos.resource;

import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.ResourcePermission;
import com.dochub.api.entities.Role;

public record ResourceRolePermissionDTO (
    ResourceResponseDTO resource,
    RoleResponseDTO role,
    ResourcePermissionResponseDTO permission
) {
    public ResourceRolePermissionDTO (final Resource resource, final Role role, final ResourcePermission resourcePermission) {
        this (
            new ResourceResponseDTO(resource),
            new RoleResponseDTO(role),
            new ResourcePermissionResponseDTO(resourcePermission)
        );
    }
}