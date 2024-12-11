package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.resource_movement.ResourceMovement;
import com.dochub.api.entities.resource_movement.ResourceMovementPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceMovementRepository extends JpaRepository<ResourceMovement, ResourceMovementPK> {
    Optional<List<ResourceMovement>> findByResource_Group (Group group);
}