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
    @Query(
        "SELECT DISTINCT g " +
        "  FROM Group g " +
        " LEFT JOIN GroupRolePermission grp ON ( grp.group.id = g.id ) " +
        " LEFT JOIN GroupPermission gp ON ( gp.id = grp.groupPermission.id ) " +
        " LEFT JOIN UserRole ur ON ( ur.role.id = grp.role.id ) " +
        "WHERE        ( ur.user = :user AND UPPER(gp.description) = 'VISUALIZAR GRUPO' AND ur.role.roleStatus = 'ATIVO' )" +
        "   OR EXISTS ( " +
                        "SELECT uc " +
                        "  FROM UserRole uc " +
                        " JOIN Role r ON ( r.id = uc.role.id ) " +
                        "WHERE uc.user = :user " +
                        "  AND UPPER(r.name) = 'ADMINISTRADOR' " +
                        "  AND r.roleStatus = 'ATIVO' " +
                     ")"
    )
    List<Group> findGroupsByUserWithViewPermission (@Param("user") User user);
}