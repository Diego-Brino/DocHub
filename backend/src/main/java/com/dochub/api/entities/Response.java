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
@Table(schema = "DOCHUB", name = "RESPOSTA")
public class Response {
    @Id
    @Column(name = "ID_RESPOSTA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO")
    private String description;

    @Embedded
    private AuditRecord auditRecord;
}