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
@Table(schema = "DOCHUB", name = "PERMISSAO_RECURSO")
public class ResourcePermission {
    @Id
    @Column(name = "ID_PERMISSAO_RECURSO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO", length = 128, nullable = false)
    private String description;

    @Embedded
    private AuditRecord auditRecord;
}