package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("SELECT DISTINCT grp.group " +
           " FROM GroupRolePermission grp " +
           "JOIN grp.role r " +
           "JOIN r.userRoles ur " +
           "WHERE ur.user = :user " +
           "  AND grp.groupPermission.description = 'Visualizar Grupo' OR ur.role.id = 1"
    )
    List<Group> findGroupsByUserWithViewPermission (@Param("user") User user);
}