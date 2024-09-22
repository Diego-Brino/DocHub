package com.dochub.api.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "RECURSO")
public class Resource {
    @Id
    @Column(name = "ID_RECURSO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME", length = 128, nullable = false)
    private String name;

    @Column(name = "DESCRICAO", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", nullable = false, insertable = false, updatable = false)
    private Group group;

    @Embedded
    private AuditRecord auditRecord;
}