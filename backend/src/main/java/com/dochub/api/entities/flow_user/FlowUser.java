package com.dochub.api.entities.flow_user;

import com.dochub.api.dtos.flow_user.CreateFlowUserDTO;
import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.User;
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
@Table(schema = "DOCHUB", name = "USUARIO_CARGO_FLUXO")
public class FlowUser {
    @EmbeddedId
    private FlowUserPK id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_FLUXO", nullable = false, insertable = false, updatable = false)
    private Flow flow;

    @Embedded
    private AuditRecord auditRecord;

    public FlowUser (final Flow flow, final User user, final String initiatorUsername) {
        this.id = new FlowUserPK(user.getId(), flow.getId());
        this.user = user;
        this.flow = flow;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}