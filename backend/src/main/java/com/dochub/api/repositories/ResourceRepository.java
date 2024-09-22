package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Optional<List<Resource>> findByGroup (Group group);
}