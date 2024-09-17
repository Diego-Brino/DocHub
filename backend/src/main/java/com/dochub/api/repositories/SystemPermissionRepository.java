package com.dochub.api.repositories;

import com.dochub.api.entities.SystemPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPermissionRepository extends JpaRepository<SystemPermission, Integer> {

}