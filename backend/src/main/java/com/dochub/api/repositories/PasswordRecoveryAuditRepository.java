package com.dochub.api.repositories;

import com.dochub.api.entities.PasswordRecoveryAudit;
import com.dochub.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRecoveryAuditRepository extends JpaRepository<PasswordRecoveryAudit, String> {
    @Query("SELECT pra " +
           " FROM PasswordRecoveryAudit  pra " +
           "WHERE pra.token = :token " +
           "  AND pra.expirationDate > CURRENT_DATE ")
    Optional<PasswordRecoveryAudit> findByToken (@Param("token") String token);

    @Query("SELECT pra " +
           " FROM PasswordRecoveryAudit pra " +
           "WHERE pra.user = :user " +
           "  AND pra.status = com.dochub.api.enums.TokenStatus.UNUSED ")
    Optional<List<PasswordRecoveryAudit>> findAllByUser (@Param("user") User user);
}