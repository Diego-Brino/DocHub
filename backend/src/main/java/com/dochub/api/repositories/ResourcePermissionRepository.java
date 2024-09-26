package com.dochub.api.repositories;

import com.dochub.api.entities.ResourcePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcePermissionRepository extends JpaRepository<ResourcePermission, Integer> {

}