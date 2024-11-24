package com.dochub.api.repositories;

import com.dochub.api.entities.flow_user.FlowUser;
import com.dochub.api.entities.flow_user.FlowUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowUserRepository extends JpaRepository<FlowUser, FlowUserPK> {

}