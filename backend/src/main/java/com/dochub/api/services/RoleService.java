package com.dochub.api.services;

import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.role.UpdateRoleDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entity.Role;
import com.dochub.api.enums.RoleStatus;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.PermissionDeniedException;
import com.dochub.api.repositories.RoleRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleResponseDTO getById (final Integer id) {
        final Role role = _getById(id);

        return new RoleResponseDTO(role);
    }

    public List<RoleResponseDTO> getAll () {
        final List<Role> roles = roleRepository.findAll();

        return roles
            .stream()
            .map(RoleResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles, final CreateRoleDTO createRoleDTO) {
        _checkPermission(userRoles, Constants.CREATE_ROLE_PERMISSION);

        final Role role = new Role(createRoleDTO, userRoles.user().username());

        return roleRepository.save(role).getId();
    }

    public void update (final UserRoleResponseDTO userRoles, final Integer roleId, final UpdateRoleDTO updateRoleDTO) {
        _checkPermission(userRoles, Constants.EDIT_ROLE_PERMISSION);

        final Role role = _getById(roleId);

        Utils.updateFieldIfPresent(updateRoleDTO.name(), role::setName);
        Utils.updateFieldIfPresent(updateRoleDTO.description(), role::setDescription);
        Utils.updateFieldIfPresent(updateRoleDTO.color(), role::setColor);
        _updateRoleStatusIfPresent(updateRoleDTO.roleStatus(), role);

        _logAuditForChange(userRoles.user().username(), role);

        roleRepository.save(role);
    }

    public void updateStatus (final UserRoleResponseDTO userRoles, final Integer roleId, final RoleStatus roleStatus) {
        _checkPermission(userRoles, Constants.EDIT_ROLE_PERMISSION);

        final Role role = _getById(roleId);

        role.setRoleStatus(roleStatus);

        _logAuditForChange(userRoles.user().username(), role);

        roleRepository.save(role);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer id) {
        _checkPermission(userRoles, Constants.DELETE_ROLE_PERMISSION);

        final Role role = _getById(id);

        roleRepository.delete(role);
    }

    private Role _getById (final Integer id) {
        return roleRepository
            .findById(id)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    private void _checkPermission (final UserRoleResponseDTO userRoles, final String permission) {
        final boolean hasPermission = userRoles
            .roles()
            .stream()
            .anyMatch(role ->
                role.systemPermissions()
                    .stream()
                    .anyMatch(sp -> Objects.equals(sp.description(), permission))
            );

        if (!hasPermission) {
            throw new PermissionDeniedException(String.format(Constants.PERMISSION_DENIED_EXCEPTION_MESSAGE, permission));
        }
    }

    private void _updateRoleStatusIfPresent (final RoleStatus roleStatus, final Role role) {
        if (Objects.nonNull(roleStatus)) {
            role.setRoleStatus(roleStatus);
        }
    }

    private void _logAuditForChange (final String actor, final Role role) {
        role.getAuditRecord().setAlterationUser(actor);
        role.getAuditRecord().setAlterationDate(new Date());
    }
}