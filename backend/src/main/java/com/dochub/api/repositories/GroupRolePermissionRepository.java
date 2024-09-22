package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.group_role_permission.GroupRolePermission;
import com.dochub.api.entities.group_role_permission.GroupRolePermissionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRolePermissionRepository extends JpaRepository<GroupRolePermission, GroupRolePermissionPK> {
    Optional<List<GroupRolePermission>> findByGroup (Group group);
}