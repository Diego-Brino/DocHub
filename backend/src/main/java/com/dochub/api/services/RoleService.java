package com.dochub.api.services;

import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.dtos.role.UpdateRoleDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Role;
import com.dochub.api.enums.RoleStatus;
import com.dochub.api.exceptions.*;
import com.dochub.api.repositories.RoleRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getById (final Integer roleId) {
        return roleRepository
            .findById(roleId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public RoleResponseDTO getDtoById (final Integer roleId) {
        final Role role = getById(roleId);

        return new RoleResponseDTO(role);
    }

    public RoleResponseDTO getByName (final String roleName) {
        final Role role = roleRepository
            .findByNameEqualsIgnoreCase(roleName)
            .orElseThrow(EntityNotFoundException::new);

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
        Utils.checkPermission(userRoles, Constants.CREATE_ROLE_PERMISSION);

        if (createRoleDTO.name().equalsIgnoreCase(Constants.ADMIN)) {
            throw new CannotCreateAdminRoleException();
        }

        final Role role = new Role(createRoleDTO, userRoles.user().username());

        return roleRepository.save(role).getId();
    }

    public void update (final UserRoleResponseDTO userRoles, final Integer roleId, final UpdateRoleDTO updateRoleDTO) {
        Utils.checkPermission(userRoles, Constants.EDIT_ROLE_PERMISSION);

        final Role role = getById(roleId);

        if (role.getName().equalsIgnoreCase(Constants.ADMIN)) {
            throw new CannotEditAdminRoleException();
        }

        Utils.updateFieldIfPresent(updateRoleDTO.name(), role::setName);
        Utils.updateFieldIfPresent(updateRoleDTO.description(), role::setDescription);
        Utils.updateFieldIfPresent(updateRoleDTO.color(), role::setColor);
        _updateRoleStatusIfPresent(updateRoleDTO.roleStatus(), role);

        _logAuditForChange(userRoles.user().username(), role);

        roleRepository.save(role);
    }

    public void updateStatus (final UserRoleResponseDTO userRoles, final Integer roleId, final RoleStatus roleStatus) {
        Utils.checkPermission(userRoles, Constants.EDIT_ROLE_PERMISSION);

        final Role role = getById(roleId);

        if (role.getName().equalsIgnoreCase(Constants.ADMIN)) {
            throw new CannotEditAdminRoleException();
        }

        role.setRoleStatus(roleStatus);

        _logAuditForChange(userRoles.user().username(), role);

        roleRepository.save(role);
    }

    public void delete (final Function<Role, Boolean> hasUsersAssignedToRoleFunc,
                        final UserRoleResponseDTO userRoles, final Integer roleId) {
        Utils.checkPermission(userRoles, Constants.DELETE_ROLE_PERMISSION);

        final Role role = getById(roleId);

        if (role.getName().equalsIgnoreCase(Constants.ADMIN)) {
            throw new CannotDeleteAdminRoleException();
        }

        final Boolean hasUsersAssignedToRole = hasUsersAssignedToRoleFunc.apply(role);

        if (hasUsersAssignedToRole) throw new RoleCannotBeDeletedException();

        roleRepository.delete(role);
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