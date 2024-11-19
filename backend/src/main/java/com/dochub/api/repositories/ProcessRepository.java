package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    Optional<List<Process>> findByService (@Param("service") final Service service);
    Optional<List<Process>> findByGroup (@Param("group") final Group group);
}