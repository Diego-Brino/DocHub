package com.dochub.api.entities.system_role_permission;

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
public class SystemRolePermissionPK {
    @Column(name = "ID_CARGO")
    private Integer idRole;

    @Column(name = "ID_PERMISSAO_SISTEMA")
    private Integer idSystemPermission;
}