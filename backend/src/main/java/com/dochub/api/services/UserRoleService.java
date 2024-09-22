package com.dochub.api.services;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.entities.user_role.UserRolePK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.UserRoleRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
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

    public Boolean hasUsersAssignedToRole (final Role role) {
        final List<UserRole> userRoles = userRoleRepository
            .findByRole(role)
            .orElse(Collections.emptyList());

        if (userRoles.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public UserRolePK create (final User user, final Integer idUser, final Integer idRole) {
        final UserRoleResponseDTO userRoles = getUserRolesByUser(user);

        Utils.checkPermission(userRoles, Constants.CREATE_USER_ROLE_PERMISSION);

        final UserRole userRole = new UserRole(idUser, idRole, user.getUsername());

        return userRoleRepository.save(userRole).getId();
    }

    public void delete (final User user, final Integer idUser, final Integer idRole) {
        final UserRoleResponseDTO userRoles = getUserRolesByUser(user);

        Utils.checkPermission(userRoles, Constants.DELETE_USER_ROLE_PERMISSION);

        final UserRolePK userRolePK = new UserRolePK(idUser, idRole);
        final UserRole userRole = _getById(userRolePK);

        userRoleRepository.delete(userRole);
    }

    private UserRole _getById (final UserRolePK userRolePK) {
        return userRoleRepository
            .findById(userRolePK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    private List<Role> _getRolesFromUserRoles (final List<UserRole> userRoles) {
        return userRoles
            .stream()
            .map(UserRole::getRole)
            .collect(Collectors.toList());
    }
}