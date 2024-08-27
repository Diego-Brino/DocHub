package com.dochub.api.services;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entity.Role;
import com.dochub.api.entity.User;
import com.dochub.api.entity.UserRole;
import com.dochub.api.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleResponseDTO getUserRolesByUser (final User user) {
        final List<UserRole> userRoles = userRoleRepository
            .findByUser(user)
            .orElse(Collections.emptyList());

        if (userRoles.isEmpty()) {
            return new UserRoleResponseDTO(user, new ArrayList<>());
        }

        final List<Role> roles = _getRolesFromUserRoles(userRoles);

        return new UserRoleResponseDTO(user, roles);
    }

    private List<Role> _getRolesFromUserRoles (final List<UserRole> userRoles) {
        return userRoles
            .stream()
            .map(UserRole::getRole)
            .collect(Collectors.toList());
    }
}