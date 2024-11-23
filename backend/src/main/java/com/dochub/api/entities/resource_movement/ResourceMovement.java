package com.dochub.api.entities.resource_movement;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Resource;
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
@Table(schema = "DOCHUB", name = "MOVIMENTO_RECURSO")
public class ResourceMovement {
    @EmbeddedId
    private ResourceMovementPK id;

    @ManyToOne
    @JoinColumn(name = "ID_MOVIMENTO", insertable = false, updatable = false)
    private Movement movement;

    @ManyToOne
    @JoinColumn(name = "ID_RECURSO", insertable = false, updatable = false)
    private Resource resource;

    @Embedded
    private AuditRecord auditRecord;
}