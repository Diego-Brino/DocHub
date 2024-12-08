package com.dochub.api.entities;

import com.dochub.api.entities.resource_movement.ResourceMovement;
import com.dochub.api.entities.response_flow.ResponseFlow;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "DOCHUB", name = "MOVIMENTO")
public class Movement {
    @Id
    @Column(name = "ID_MOVIMENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_SOLICITACAO", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_FLUXO", referencedColumnName = "ID_FLUXO"),
        @JoinColumn(name = "ID_RESPOSTA", referencedColumnName = "ID_RESPOSTA")
    })
    private ResponseFlow responseFlow;

    @Column(name = "ORDEM", nullable = false)
    private Integer order;

    @OneToMany(mappedBy = "movement", cascade = { CascadeType.REMOVE })
    private List<ResourceMovement> resourceMovements;

    @Embedded
    private AuditRecord auditRecord;

    public Movement (final Request request, final ResponseFlow responseFlow, final Integer order, final String initiatorUsername) {
        this.request = request;
        this.responseFlow = responseFlow;
        this.order = order;

        this.auditRecord = AuditRecord
            .builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}