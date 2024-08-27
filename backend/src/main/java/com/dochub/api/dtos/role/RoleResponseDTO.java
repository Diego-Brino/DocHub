package com.dochub.api.dtos.role;

import com.dochub.api.dtos.system_permission.SystemPermissionResponseDTO;
import com.dochub.api.entity.Role;
import com.dochub.api.entity.SystemRolePermission;

import java.util.List;
import java.util.stream.Collectors;

public record RoleResponseDTO (
        Integer id,
        String name,
        String description,
        String color,
        String status,
        List<SystemPermissionResponseDTO> systemPermissions) {

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
                .collect(Collectors.toList())
        );
    }
}