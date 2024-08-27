package com.dochub.api.repositories;

import com.dochub.api.entity.User;
import com.dochub.api.entity.UserRole;
import com.dochub.api.entity.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {
    Optional<List<UserRole>> findByUser (User user);
}