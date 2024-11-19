package com.dochub.api.repositories;

import com.dochub.api.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}