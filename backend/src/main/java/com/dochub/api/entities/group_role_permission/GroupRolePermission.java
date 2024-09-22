package com.dochub.api.entities.group_role_permission;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.GroupPermission;
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
@Table(schema = "DOCHUB", name = "CARGO_PERMISSAO_GRUPO")
public class GroupRolePermission {
    @EmbeddedId
    private GroupRolePermissionPK id;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "ID_PERMISSAO_GRUPO", insertable = false, updatable = false)
    private GroupPermission groupPermission;

    @ManyToOne
    @JoinColumn(name = "ID_GRUPO", insertable = false, updatable = false)
    private Group group;

    @Embedded
    private AuditRecord auditRecord;

    public GroupRolePermission (final Integer idRole, final Integer idGroupPermission, final Integer idGroup, final String initiatorUsername) {
        this.id = new GroupRolePermissionPK(idRole, idGroupPermission, idGroup);

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}