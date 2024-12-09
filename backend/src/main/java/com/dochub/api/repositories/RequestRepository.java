package com.dochub.api.repositories;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.Request;
import com.dochub.api.entities.Service;
import com.dochub.api.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<List<Request>> findByProcess (Process process);
    Optional<List<Request>> findByProcess_Group (Group group);
    Optional<List<Request>> findByProcess_GroupAndStatus (Group group, RequestStatus requestStatus);

    @Query("""
        SELECT r
          FROM Request r
        WHERE r.process = :process
          AND r.status = 'EM ANDAMENTO'  
    """)
    Optional<List<Request>> findByProcessAndStatus_InProgress (Process process);

    @Query("""
        SELECT r
          FROM Request r
        WHERE r.process.service = :service
          AND r.status = 'EM ANDAMENTO'  
    """)
    Optional<List<Request>> findByProcess_ServiceAndStatus_InProgress (Service service);

    Optional<List<Request>> findByProcess_Service (Service service);
}