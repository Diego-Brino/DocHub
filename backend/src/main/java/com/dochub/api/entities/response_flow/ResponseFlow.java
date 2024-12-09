package com.dochub.api.entities.response_flow;

import com.dochub.api.dtos.response_flow.CreateResponseFlowDTO;
import com.dochub.api.entities.*;
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
@Table(schema = "DOCHUB", name = "FLUXO_RESPOSTA")
public class ResponseFlow {
    @EmbeddedId
    private ResponseFlowPK id;

    @ManyToOne
    @JoinColumn(name = "ID_FLUXO", insertable = false, updatable = false)
    private Flow flow;

    @ManyToOne
    @JoinColumn(name = "ID_RESPOSTA", insertable = false, updatable = false)
    private Response response;

    @ManyToOne
    @JoinColumn(name = "ID_FLUXO_DESTINO")
    private Flow destinationFlow;

    @Embedded
    private AuditRecord auditRecord;

    public ResponseFlow (final Flow flow, final Response response, final Flow destinationFlow, final String initiatorUsername) {
        this.id = new ResponseFlowPK(flow.getId(), response.getId());
        this.flow = flow;
        this.response = response;
        this.destinationFlow = destinationFlow;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}