package com.dochub.api.repositories;

import com.dochub.api.entities.GroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Integer> {
    Optional<GroupPermission> findByDescriptionEqualsIgnoreCase (String description);
}