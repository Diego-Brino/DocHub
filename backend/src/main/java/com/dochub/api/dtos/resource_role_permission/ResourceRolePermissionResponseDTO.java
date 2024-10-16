package com.dochub.api.dtos.resource_role_permission;

import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.ResourcePermission;

import java.util.List;

public record ResourceRolePermissionResponseDTO (
    ResourceResponseDTO resource,
    List<ResourcePermissionResponseDTO> permissions
) {
    public ResourceRolePermissionResponseDTO (final Resource resource, final List<ResourcePermission> resourcePermissions) {
        this (
            new ResourceResponseDTO(resource),
            resourcePermissions
                .stream()
                .map(ResourcePermissionResponseDTO::new)
                .toList()
        );
    }
}