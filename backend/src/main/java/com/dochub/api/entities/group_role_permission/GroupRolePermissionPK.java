package com.dochub.api.entities.group_role_permission;

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
public class GroupRolePermissionPK {
    @Column(name = "ID_CARGO")
    private Integer idRole;

    @Column(name = "ID_PERMISSAO_GRUPO")
    private Integer idGroupPermission;

    @Column(name = "ID_GRUPO")
    private Integer idGroup;
}