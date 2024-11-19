package com.dochub.api.repositories;

import com.dochub.api.entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Integer> {

}