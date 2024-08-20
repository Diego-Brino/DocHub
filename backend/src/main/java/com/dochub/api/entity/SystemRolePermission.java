package com.dochub.api.entity;

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
@Table(schema = "DOCHUB", name = "CARGO_PERMISSAO_SISTEMA")
public class SystemRolePermission {
    @Id
    @Column(name = "ID_CARGO_PERMISSAO_SISTEMA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_PERMISSAO_SISTEMA")
    private SystemPermission systemPermission;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO")
    private Role role;

    @Embedded
    private AuditRecord auditRecord;
}