package com.dochub.api.entities;

import com.dochub.api.converters.TokenStatusConverter;
import com.dochub.api.enums.TokenStatus;
import com.dochub.api.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "AUDITORIA_RECUPERACAO_SENHA")
public class PasswordRecoveryAudit {
    @Id
    @Column(name = "TOKEN")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private User user;

    @Column(name = "DATA_EXPIRACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "STATUS")
    @Convert(converter = TokenStatusConverter.class)
    private TokenStatus status;

    public PasswordRecoveryAudit(final User user) {
        this.user = user;
        this.expirationDate = new Date(System.currentTimeMillis() + Constants.RECOVER_PASSWORD_TOKEN_EXPIRATION_TIME_MILLIS);
        this.status = TokenStatus.UNUSED;
    }
}