package com.dochub.api.entities.resource_movement;

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
public class ResourceMovementPK {
    @Column(name = "ID_MOVIMENTO")
    private Integer idMovimento;

    @Column(name = "ID_RECURSO")
    private Integer idRecurso;
}