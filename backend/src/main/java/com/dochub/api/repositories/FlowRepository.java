package com.dochub.api.repositories;

import com.dochub.api.entities.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer> {

}