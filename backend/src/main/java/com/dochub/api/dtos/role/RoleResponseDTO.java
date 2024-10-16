package com.dochub.api.dtos.role;

import com.dochub.api.dtos.group_role_permission.GroupRolePermissionResponseDTO;
import com.dochub.api.dtos.resource_role_permission.ResourceRolePermissionResponseDTO;
import com.dochub.api.dtos.system_permission.SystemPermissionResponseDTO;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.system_role_permission.SystemRolePermission;

import java.util.List;
import java.util.stream.Collectors;

public record RoleResponseDTO (
    Integer id,
    String name,
    String description,
    String color,
    String status,
    List<SystemPermissionResponseDTO> systemPermissions,
    List<GroupRolePermissionResponseDTO> groupPermissions,
    List<ResourceRolePermissionResponseDTO> resourcePermissions
) {
    public RoleResponseDTO (final Role role) {
        this(
            role.getId(),
            role.getName(),
            role.getDescription(),
            role.getColor(),
            role.getRoleStatus().getCode(),
            role.getSystemRolePermissions()
                .stream()
                .map(SystemRolePermission::getSystemPermission)
                .map(SystemPermissionResponseDTO::new)
                .collect(Collectors.toList()),
            role.getGroupPermissionsGroupedByGroup()
                .entrySet()
                .stream()
                .map(entry -> new GroupRolePermissionResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()),
            role.getResourcePermissionsGroupedByResource()
                .entrySet()
                .stream()
                .map(entry -> new ResourceRolePermissionResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList())
        );
    }
}