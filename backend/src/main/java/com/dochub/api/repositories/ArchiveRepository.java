package com.dochub.api.repositories;

import com.dochub.api.entities.Archive;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Integer> {
    @Query("SELECT DISTINCT a " +
           " FROM Archive a " +
           "LEFT JOIN ResourceRolePermission rrp ON a.resource.id = rrp.resource.id " +
           "LEFT JOIN rrp.resourcePermission rp ON rp.description = 'Visualizar Recurso' " +
           "LEFT JOIN rrp.role.userRoles ur ON ur.user = :user " +
           "WHERE a.id = :idArchive " +
           "  AND (rp IS NULL OR ur.user.id IS NOT NULL) ")
    Optional<Archive> findById (@Param("user") User user, @Param("idArchive") Integer idArchive);

    @Query("SELECT a " +
           " FROM Archive a " +
           "LEFT JOIN ResourceRolePermission rrp ON a.resource.id = rrp.resource.id " +
           "LEFT JOIN rrp.resourcePermission rp ON rp.description = 'Visualizar Recurso' " +
           "LEFT JOIN rrp.role.userRoles ur ON ur.user = :user " +
           "WHERE a.resource.group = :group " +
           "  AND (a.folder IS NULL) " +
           "  AND (rp IS NULL OR ur.user.id IS NOT NULL) ")
    Optional<List<Archive>> findByResource_GroupAndFolderIsNullWithPermission (@Param("group") Group group, @Param("user") User user);

    @Query("SELECT a " +
           " FROM Archive a " +
           "LEFT JOIN ResourceRolePermission rrp ON a.resource.id = rrp.resource.id " +
           "LEFT JOIN rrp.resourcePermission rp ON rp.description = 'Visualizar Recurso' " +
           "LEFT JOIN rrp.role.userRoles ur ON ur.user = :user " +
           "WHERE a.resource.group = :group " +
           "  AND a.folder = :folder " +
           "  AND (rp IS NULL OR ur.user.id IS NOT NULL) ")
    Optional<List<Archive>> findByResource_GroupAndFolderWithPermission (@Param("group") Group group, @Param("folder") Folder folder, @Param("user") User user);
}