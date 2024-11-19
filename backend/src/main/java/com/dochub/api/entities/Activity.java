package com.dochub.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "ATIVIDADE")
public class Activity {
    @Id
    @Column(name = "ID_ATIVIDADE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO")
    private String description;

    @Embedded
    private AuditRecord auditRecord;
}