package com.dochub.api.repositories;

import com.dochub.api.entities.Flow;
import com.dochub.api.entities.User;
import com.dochub.api.entities.flow_user.FlowUser;
import com.dochub.api.entities.flow_user.FlowUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowUserRepository extends JpaRepository<FlowUser, FlowUserPK> {
    @Query("""
        SELECT fu.flow 
           FROM FlowUser fu
         LEFT JOIN Request r ON ( r.process.id = fu.flow.process.id )
         LEFT JOIN Movement m ON ( m.responseFlow.flow.id = fu.flow.id )
        WHERE fu.user = :user
          AND UPPER(r.status) = 'EM ANDAMENTO'
          AND m IS NULL
    """)
    Optional<List<Flow>> findFlowsInProgressByUser(User user);

    Optional<List<FlowUser>> findByFlow(Flow flow);
}