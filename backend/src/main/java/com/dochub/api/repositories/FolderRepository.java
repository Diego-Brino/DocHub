package com.dochub.api.repositories;

import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
    @Query("SELECT f " +
           " FROM Folder f " +
           "LEFT JOIN ResourceRolePermission rrp ON f.resource.id = rrp.resource.id " +
           "LEFT JOIN rrp.resourcePermission rp ON rp.description = 'Visualizar Recurso' " +
           "LEFT JOIN rrp.role.userRoles ur ON ur.user = :user " +
           "WHERE f.resource.group = :group " +
           "  AND f.parentFolder IS NULL " +
           "  AND (rp IS NULL OR ur.user.id IS NOT NULL) ")
    Optional<List<Folder>> findByResource_GroupAndParentFolderIsNullWithPermission (@Param("group") Group group, @Param("user") User user);

    @Query("SELECT f " +
           " FROM Folder f " +
           "LEFT JOIN ResourceRolePermission rrp ON f.resource.id = rrp.resource.id " +
           "LEFT JOIN rrp.resourcePermission rp ON rp.description = 'Visualizar Recurso' " +
           "LEFT JOIN rrp.role.userRoles ur ON ur.user = :user " +
           "WHERE f.resource.group = :group " +
           "  AND f.parentFolder = :parentFolder " +
           "  AND (rp IS NULL OR ur.user.id IS NOT NULL) ")
    Optional<List<Folder>> findByResource_GroupAndParentFolderWithPermission (@Param("group") Group group, @Param("parentFolder") Folder parentFolder, @Param("user") User user);
}