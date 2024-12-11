package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {
    Optional<List<Movement>> findMovementByRequest_IdOrderByOrderDesc (Integer id);
    Optional<List<Movement>> findByRequest_Process_Group (Group group);
}