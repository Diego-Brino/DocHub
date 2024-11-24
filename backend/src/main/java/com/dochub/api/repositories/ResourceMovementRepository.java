package com.dochub.api.repositories;

import com.dochub.api.entities.resource_movement.ResourceMovement;
import com.dochub.api.entities.resource_movement.ResourceMovementPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceMovementRepository extends JpaRepository<ResourceMovement, ResourceMovementPK> {

}