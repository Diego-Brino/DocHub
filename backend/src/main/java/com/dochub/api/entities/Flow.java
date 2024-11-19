package com.dochub.api.entities;

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
@Table(schema = "DOCHUB", name = "FLUXO")
public class Flow {
    @Id
    @Column(name = "ID_FLUXO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ORDEM")
    private Integer order;

    @Column(name = "PRAZO")
    private Integer time;

    @Column(name = "DATA_LIMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitDate;

    @ManyToOne
    @JoinColumn(name = "ID_PROCESSO", nullable = false, insertable = false, updatable = false)
    private Process process;

    @ManyToOne
    @JoinColumn(name = "ID_ATIVIDADE", nullable = false, insertable = false, updatable = false)
    private Activity activity;

    @Embedded
    private AuditRecord auditRecord;
}