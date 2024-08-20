package com.dochub.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PERMISSAO_SISTEMA")
public class SystemPermission {
    @Id
    @Column(name = "ID_PERMISSAO_SISTEMA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRICAO", length = 128)
    private String description;

    @Embedded
    private AuditRecord auditRecord;
}