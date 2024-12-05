package com.dochub.api.repositories;

import com.dochub.api.dtos.resource.ResourceRolePermissionDTO;
import com.dochub.api.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    @Query("""
        SELECT new com.dochub.api.dtos.resource.ResourceRolePermissionDTO(r, grr.role, grr.resourcePermission)
           FROM Resource r
        LEFT JOIN ResourceRolePermission grr ON ( grr.resource.id = r.id )
        WHERE r.id = :resourceId
    """)
    Optional<List<ResourceRolePermissionDTO>> findResourceRolesPermissionsByResourceId(Integer resourceId);
}