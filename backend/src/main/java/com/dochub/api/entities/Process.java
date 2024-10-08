package com.dochub.api.entities;

import com.dochub.api.converters.ProcessStatusConverter;
import com.dochub.api.converters.RoleStatusConverter;
import com.dochub.api.enums.ProcessStatus;
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

    @Column(name = "STATUS")
    @Convert(converter = ProcessStatusConverter.class)
    private ProcessStatus processStatus;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICO", nullable = false, insertable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", nullable = false, insertable = false, updatable = false)
    private Group group;

    @Embedded
    private AuditRecord auditRecord;
}