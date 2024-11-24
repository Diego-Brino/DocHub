package com.dochub.api.entities.flow_user;

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
public class FlowUserPK {
    @Column(name = "ID_USUARIO")
    private Integer idUser;

    @Column(name = "ID_FLUXO")
    private Integer idFlow;
}