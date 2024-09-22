package com.dochub.api.services;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.group_role_permission.GroupRolePermission;
import com.dochub.api.entities.group_role_permission.GroupRolePermissionPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.GroupRolePermissionRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupRolePermissionService {
    private final GroupRolePermissionRepository groupRolePermissionRepository;

    public Boolean hasGroupRolePermissionsAssignedToGroup (final Group group) {
        final List<GroupRolePermission> groupRolePermissions = groupRolePermissionRepository
            .findByGroup(group)
            .orElse(Collections.emptyList());

        if (groupRolePermissions.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public GroupRolePermissionPK create (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idGroupPermission, final Integer idGroup) {
        Utils.checkPermission(userRoles, Constants.CREATE_GROUP_ROLE_PERMISSION);

        final GroupRolePermission groupRolePermission = new GroupRolePermission(idRole, idGroupPermission, idGroup, userRoles.user().username());

        return groupRolePermissionRepository.save(groupRolePermission).getId();
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idGroupPermission, final Integer idGroup) {
        Utils.checkPermission(userRoles, Constants.DELETE_GROUP_ROLE_PERMISSION);

        final GroupRolePermissionPK groupRolePermissionPK = new GroupRolePermissionPK(idRole, idGroupPermission, idGroup);
        final GroupRolePermission groupRolePermission = _getById(groupRolePermissionPK);

        groupRolePermissionRepository.delete(groupRolePermission);
    }

    private GroupRolePermission _getById (final GroupRolePermissionPK groupRolePermissionPK) {
        return groupRolePermissionRepository
            .findById(groupRolePermissionPK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }
}