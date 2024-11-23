package com.dochub.api.entities.flow_user_role;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.user_role.UserRole;
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
@Table(schema = "DOCHUB", name = "USUARIO_CARGO_FLUXO")
public class FlowUserRole {
    @EmbeddedId
    private FlowUserRolePK id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", nullable = false, insertable = false, updatable = false)
    })
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "ID_FLUXO", nullable = false, insertable = false, updatable = false)
    private Flow flow;

    @Embedded
    private AuditRecord auditRecord;
}