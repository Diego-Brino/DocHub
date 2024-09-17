package com.dochub.api.dtos.user_roles;

import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;

import java.util.List;

public record UserRoleResponseDTO (
    UserResponseDTO user,
    List<RoleResponseDTO> roles
) {
    public UserRoleResponseDTO (final User user, final List<Role> roles) {
        this (
            new UserResponseDTO(user),
            roles
                .stream()
                .map(RoleResponseDTO::new)
                .toList()
        );
    }
}