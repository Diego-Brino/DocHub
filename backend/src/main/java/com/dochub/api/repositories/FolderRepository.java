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
    @Query("""
        SELECT f
          FROM Folder f
        WHERE f.resource.group = :group
          AND f.parentFolder IS NULL
          AND (
                EXISTS        (
                                SELECT uc
                                  FROM UserRole uc
                                WHERE uc.user = :user
                                  AND UPPER(uc.role.name) = 'ADMINISTRADOR'
                                  AND uc.role.roleStatus = 'ATIVO'                
                              )
                OR EXISTS     (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                 JOIN Role r ON ( r.id = rrp.role.id )
                                 JOIN UserRole ur ON ( ur.user = :user AND ur.role.id = r.id )
                                WHERE UPPER(rrp.resourcePermission.description) = 'VISUALIZAR PASTA' 
                                  AND r.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = f.resource.id
                              )
                OR NOT EXISTS (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                WHERE rrp.role.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = f.resource.id
                                  AND UPPER(rrp.resourcePermission.description) = 'VISUALIZAR PASTA' 
                              )  
              )                      
    """)
    Optional<List<Folder>> findByResource_GroupAndParentFolderIsNullWithPermission (@Param("group") Group group, @Param("user") User user);

    @Query("""
        SELECT f
          FROM Folder f
        WHERE f.resource.group = :group
          AND f.parentFolder = :parentFolder
          AND (
                EXISTS        (
                                SELECT uc
                                  FROM UserRole uc
                                WHERE uc.user = :user
                                  AND UPPER(uc.role.name) = 'ADMINISTRADOR'
                                  AND uc.role.roleStatus = 'ATIVO'                
                              )
                OR EXISTS     (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                 JOIN Role r ON ( r.id = rrp.role.id )
                                 JOIN UserRole ur ON ( ur.user = :user AND ur.role.id = r.id )
                                WHERE UPPER(rrp.resourcePermission.description) = 'VISUALIZAR PASTA' 
                                  AND r.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = f.resource.id
                              )
                OR NOT EXISTS (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                WHERE rrp.role.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = f.resource.id
                                  AND UPPER(rrp.resourcePermission.description) = 'VISUALIZAR PASTA' 
                              )  
              )                      
    """)
    Optional<List<Folder>> findByResource_GroupAndParentFolderWithPermission (@Param("group") Group group, @Param("parentFolder") Folder parentFolder, @Param("user") User user);

    Optional<List<Folder>> findByResource_Group (@Param("group") Group group);
}