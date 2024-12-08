package com.dochub.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "PROCESSO")
public class Process {
    @Id
    @Column(name = "ID_PROCESSO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICO", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "process", cascade = { CascadeType.REMOVE })
    private List<Flow> flows;

    @OneToMany(mappedBy = "process", cascade = { CascadeType.REMOVE })
    private List<Request> requests;

    @Embedded
    private AuditRecord auditRecord;

    public Process (final Service service, final Group group, final String initiatorUsername) {
        this.startDate = new Date();
        this.service = service;
        this.group = group;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}