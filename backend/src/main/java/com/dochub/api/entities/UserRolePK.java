package com.dochub.api.entities;

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
public class UserRolePK {
    @Column(name = "ID_USUARIO")
    private Integer idUser;

    @Column(name = "ID_CARGO")
    private Integer idRole;
}