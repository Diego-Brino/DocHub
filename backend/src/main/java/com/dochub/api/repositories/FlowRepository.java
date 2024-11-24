package com.dochub.api.repositories;

import com.dochub.api.entities.Activity;
import com.dochub.api.entities.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer> {
    Optional<List<Flow>> findByActivity (Activity activity);
}