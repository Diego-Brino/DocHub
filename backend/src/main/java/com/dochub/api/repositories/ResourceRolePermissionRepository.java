package com.dochub.api.repositories;

import com.dochub.api.entities.Resource;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermissionPK;
import com.dochub.api.services.ResourceRolePermissionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRolePermissionRepository extends JpaRepository<ResourceRolePermission, ResourceRolePermissionPK> {
    Optional<List<ResourceRolePermission>> findAllByResource (Resource resource);
}