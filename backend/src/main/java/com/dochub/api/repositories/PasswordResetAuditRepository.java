package com.dochub.api.repositories;

import com.dochub.api.entity.PasswordResetAudit;
import com.dochub.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetAuditRepository  extends JpaRepository<PasswordResetAudit, String> {
    @Query("SELECT pra " +
           " FROM PasswordResetAudit  pra " +
           "WHERE pra.token = :token " +
           "  AND pra.expirationDate > CURRENT_DATE ")
    Optional<PasswordResetAudit> findByToken (@Param("token") String token);

    @Query("SELECT pra " +
           " FROM PasswordResetAudit pra " +
           "WHERE pra.user = :user " +
           "  AND pra.status = com.dochub.api.enums.TokenStatus.UNUSED ")
    Optional<List<PasswordResetAudit>> findAllByUser (@Param("user") User user);
}