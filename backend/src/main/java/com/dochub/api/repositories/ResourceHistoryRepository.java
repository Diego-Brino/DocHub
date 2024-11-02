package com.dochub.api.repositories;

import com.dochub.api.entities.ResourceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceHistoryRepository extends JpaRepository<ResourceHistory, Integer> {

}