package com.dochub.api.services;

import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermissionPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourceRolePermissionRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceRolePermissionService {
    private final ResourceRolePermissionRepository resourceRolePermissionRepository;

    public List<ResourceRolePermission> getAllByResource (final Resource resource) {
        return resourceRolePermissionRepository
            .findAllByResource(resource)
            .orElse(Collections.emptyList());
    }

    public ResourceRolePermissionPK create (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idResourcePermission, final Integer idResource) {
        Utils.checkPermission(userRoles, Constants.CREATE_RESOURCE_ROLE_PERMISSION);

        final ResourceRolePermission resourceRolePermission = new ResourceRolePermission(idRole, idResourcePermission, idResource, userRoles.user().username());

        return resourceRolePermissionRepository.save(resourceRolePermission).getId();
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer idRole, final Integer idResourcePermission, final Integer idResource) {
        Utils.checkPermission(userRoles, Constants.DELETE_RESOURCE_ROLE_PERMISSION);

        final ResourceRolePermissionPK resourceRolePermissionPK = new ResourceRolePermissionPK(idRole, idResourcePermission, idResource);
        final ResourceRolePermission resourceRolePermission = _getById(resourceRolePermissionPK);

        resourceRolePermissionRepository.delete(resourceRolePermission);
    }

    public void delete (final UserRoleResponseDTO userRoles, final List<ResourceRolePermission> resourceRolePermissions) {
        Utils.checkPermission(userRoles, Constants.DELETE_RESOURCE_ROLE_PERMISSION);

        resourceRolePermissionRepository.deleteAll(resourceRolePermissions);
    }

    private ResourceRolePermission _getById (final ResourceRolePermissionPK resourceRolePermissionPK) {
        return resourceRolePermissionRepository
            .findById(resourceRolePermissionPK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }
}