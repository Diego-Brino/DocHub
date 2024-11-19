package com.dochub.api.entities.response_flow;

import com.dochub.api.entities.AuditRecord;
import com.dochub.api.entities.Flow;
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
@Table(schema = "DOCHUB", name = "FLUXO_RESPOSTA")
public class ResponseFlow {
    @EmbeddedId
    private ResponseFlowPK id;

    @ManyToOne
    @JoinColumn(name = "ID_FLUXO_DESTINO", insertable = false, updatable = false)
    private Flow destinationFlow;

    @Embedded
    private AuditRecord auditRecord;
}