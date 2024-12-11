package com.dochub.api.repositories;

import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Response;
import com.dochub.api.entities.response_flow.ResponseFlow;
import com.dochub.api.entities.response_flow.ResponseFlowPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseFlowRepository extends JpaRepository<ResponseFlow, ResponseFlowPK> {
    Optional<List<ResponseFlow>> findByResponse (Response response);
    Optional<List<ResponseFlow>> findByDestinationFlow (Flow flow);
    Optional<List<ResponseFlow>> findByFlow_Process_Group (Group group);
}