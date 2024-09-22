package com.dochub.api.services;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.system_role_permission.SystemRolePermission;
import com.dochub.api.entities.system_role_permission.SystemRolePermissionPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.SystemRolePermissionRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemRolePermissionService {
    private final SystemRolePermissionRepository systemRolePermissionRepository;

    public SystemRolePermissionPK create (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idSystemPermission) {
        Utils.checkPermission(userRoles, Constants.CREATE_SYSTEM_ROLE_PERMISSION);

        final SystemRolePermission systemRolePermission = new SystemRolePermission(idRole, idSystemPermission, userRoles.user().username());

        return systemRolePermissionRepository.save(systemRolePermission).getId();
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idSystemPermission) {
        Utils.checkPermission(userRoles, Constants.DELETE_SYSTEM_ROLE_PERMISSION);

        final SystemRolePermissionPK systemRolePermissionPK = new SystemRolePermissionPK(idRole, idSystemPermission);
        final SystemRolePermission systemRolePermission = _getById(systemRolePermissionPK);

        systemRolePermissionRepository.delete(systemRolePermission);
    }

    private SystemRolePermission _getById (final SystemRolePermissionPK systemRolePermissionPK) {
        return systemRolePermissionRepository
            .findById(systemRolePermissionPK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }
}