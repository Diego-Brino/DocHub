package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.ResourceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceHistoryRepository extends JpaRepository<ResourceHistory, Integer> {
    Optional<List<ResourceHistory>> findAllByGroupOrderByActionDateDesc (Group group);
}