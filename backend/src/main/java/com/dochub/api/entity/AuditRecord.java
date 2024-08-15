package com.dochub.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AuditRecord {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INSERCAO")
    private Date insertionDate;

    @Column(name = "USUARIO_INSERCAO", length = 100)
    private String insertionUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ALTERACAO")
    private Date alterationDate;

    @Column(name = "USUARIO_ALTERACAO", length = 100)
    private String alterationUser;
}