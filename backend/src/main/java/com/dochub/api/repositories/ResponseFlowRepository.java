package com.dochub.api.repositories;

import com.dochub.api.entities.response_flow.ResponseFlow;
import com.dochub.api.entities.response_flow.ResponseFlowPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseFlowRepository extends JpaRepository<ResponseFlow, ResponseFlowPK> {

}