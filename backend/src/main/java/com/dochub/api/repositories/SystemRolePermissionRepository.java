package com.dochub.api.repositories;

import com.dochub.api.entities.system_role_permission.SystemRolePermission;
import com.dochub.api.entities.system_role_permission.SystemRolePermissionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRolePermissionRepository extends JpaRepository<SystemRolePermission, SystemRolePermissionPK> {

}