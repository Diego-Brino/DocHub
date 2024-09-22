package com.dochub.api.repositories;

import com.dochub.api.entities.Role;
import com.dochub.api.entities.User;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.entities.user_role.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {
    Optional<List<UserRole>> findByUser (User user);
    Optional<List<UserRole>> findByRole (Role role);
}