package com.dochub.api.repositories;

import com.dochub.api.entities.SystemRolePermission;
import com.dochub.api.entities.SystemRolePermissionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRolePermissionRepository extends JpaRepository<SystemRolePermission, SystemRolePermissionPK> {

}