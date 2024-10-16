package com.dochub.api.entities;

import com.dochub.api.converters.ResourceHistoryActionConverter;
import com.dochub.api.enums.ResourceHistoryAction;
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
@Table(schema = "DOCHUB", name = "HISTORICO")
public class ResourceHistory {
    @Id
    @Column(name = "ID_HISTORICO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INICIO")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_FIM")
    private Date endDate;

    @Column(name = "ACAO")
    @Convert(converter = ResourceHistoryActionConverter.class)
    private ResourceHistoryAction action;

    @ManyToOne
    @JoinColumn(name = "ID_RECURSO", nullable = false)
    private Resource resource;

    @Embedded
    private AuditRecord auditRecord;
}