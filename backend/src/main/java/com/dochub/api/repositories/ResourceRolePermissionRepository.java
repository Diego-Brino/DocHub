package com.dochub.api.repositories;

import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermissionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRolePermissionRepository extends JpaRepository<ResourceRolePermission, ResourceRolePermissionPK> {

}