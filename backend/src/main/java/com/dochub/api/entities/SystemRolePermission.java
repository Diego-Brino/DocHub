package com.dochub.api.entities;

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
@Table(schema = "DOCHUB", name = "CARGO_PERMISSAO_SISTEMA")
public class SystemRolePermission {
    @EmbeddedId
    private SystemRolePermissionPK id;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "ID_PERMISSAO_SISTEMA", insertable = false, updatable = false)
    private SystemPermission systemPermission;

    @Embedded
    private AuditRecord auditRecord;

    public SystemRolePermission (final Integer idRole, final Integer idSystemPermission, final String initiatorUsername) {
        this.id = new SystemRolePermissionPK(idRole, idSystemPermission);

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}