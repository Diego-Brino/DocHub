package com.dochub.api.entities.response_flow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ResponseFlowPK {
    @Column(name = "ID_FLUXO")
    private Integer idFlow;

    @Column(name = "ID_RESPOSTA")
    private Integer idResponse;
}