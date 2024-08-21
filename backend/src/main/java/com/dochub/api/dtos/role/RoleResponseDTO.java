package com.dochub.api.dtos.role;

import com.dochub.api.entity.Role;

public record RoleResponseDTO (
    String name,
    String description,
    String color) {

    public RoleResponseDTO (final Role role) {
        this(
            role.getName(),
            role.getDescription(),
            role.getColor()
        );
    }
}