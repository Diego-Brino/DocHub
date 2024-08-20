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
@Table(name = "USUARIO_CARGO")
public class UserRole {
    @EmbeddedId
    private UserRolePK id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO", insertable = false, updatable = false)
    private Role role;

    @Embedded
    private AuditRecord auditRecord;
}