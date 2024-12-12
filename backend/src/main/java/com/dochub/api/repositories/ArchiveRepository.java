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
    @Query("""
        SELECT a
          FROM Archive a
        WHERE a.id = :idArchive
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
                                WHERE UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                                  AND r.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                              )
                OR NOT EXISTS (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                WHERE rrp.role.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                                  AND UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                              )  
              )                      
    """)
    Optional<Archive> findById (@Param("user") User user, @Param("idArchive") Integer idArchive);

    @Query("""
        SELECT a
          FROM Archive a
        WHERE a.resource.group = :group
          AND a.folder IS NULL
          AND a.resource.origin = 'GRUPO'
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
                                WHERE UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                                  AND r.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                              )
                OR NOT EXISTS (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                WHERE rrp.role.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                                  AND UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                              )  
              )                      
    """)
    Optional<List<Archive>> findByResource_GroupAndFolderIsNullWithPermission (@Param("group") Group group, @Param("user") User user);

    @Query("""
        SELECT a
          FROM Archive a
        WHERE a.resource.group = :group
          AND a.folder = :folder
          AND a.resource.origin = 'GRUPO'
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
                                WHERE UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                                  AND r.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                              )
                OR NOT EXISTS (
                                SELECT rrp
                                  FROM ResourceRolePermission rrp
                                WHERE rrp.role.roleStatus = 'ATIVO'
                                  AND rrp.resource.id = a.resource.id
                                  AND UPPER(rrp.resourcePermission.description) = 'VISUALIZAR ARQUIVO' 
                              )  
              )                      
    """)
    Optional<List<Archive>> findByResource_GroupAndFolderWithPermission (@Param("group") Group group, @Param("folder") Folder folder, @Param("user") User user);

    Optional<List<Archive>> findByResource_Group (@Param("group") Group group);

    Optional<Archive> findByS3Hash (String s3Hash);
}