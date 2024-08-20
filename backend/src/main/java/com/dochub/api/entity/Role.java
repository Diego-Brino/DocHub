package com.dochub.api.entity;

import com.dochub.api.enums.RoleStatus;
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
@Table(name = "CARGO")
public class Role {
    @Id
    @Column(name = "ID_CARGO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME", length = 128)
    private String name;

    @Column(name = "DESCRICAO", length = 256)
    private String description;

    @Column(name = "COR", length = 32)
    private String color;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    @Embedded
    private AuditRecord auditRecord;
}