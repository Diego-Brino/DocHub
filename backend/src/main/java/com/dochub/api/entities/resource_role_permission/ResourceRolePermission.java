package com.dochub.api.entities.resource_role_permission;

import com.dochub.api.entities.*;
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
@Table(schema = "DOCHUB", name = "CARGO_PERMISSAO_RECURSO")
public class ResourceRolePermission {
    @EmbeddedId
    private ResourceRolePermissionPK id;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "ID_PERMISSAO_RECURSO", insertable = false, updatable = false)
    private ResourcePermission resourcePermission;

    @ManyToOne
    @JoinColumn(name = "ID_RECURSO", insertable = false, updatable = false)
    private Resource resource;

    @Embedded
    private AuditRecord auditRecord;
}