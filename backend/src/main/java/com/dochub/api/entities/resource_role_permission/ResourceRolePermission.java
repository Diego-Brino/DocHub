package com.dochub.api.entities.resource_role_permission;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.ResourcePermission;
import com.dochub.api.entities.Role;
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

    public ResourceRolePermission (final Integer idRole, final Integer idResourcePermission, final Integer idResource, final String initiatorUsername) {
        this.id = new ResourceRolePermissionPK(idRole, idResourcePermission, idResource);

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}