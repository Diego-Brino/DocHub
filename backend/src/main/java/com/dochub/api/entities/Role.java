package com.dochub.api.entities;

import com.dochub.api.converters.RoleStatusConverter;
import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.enums.RoleStatus;
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
@Table(schema = "DOCHUB", name = "CARGO")
public class Role {
    @Id
    @Column(name = "ID_CARGO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME", length = 128)
    private String name;

    @Column(name = "DESCRICAO", length = 256)
    private String description;

    @Column(name = "COR", length = 32)
    private String color;

    @Column(name = "STATUS")
    @Convert(converter = RoleStatusConverter.class)
    private RoleStatus roleStatus;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "role", cascade = { CascadeType.REMOVE })
    private List<SystemRolePermission> systemRolePermissions;

    @Embedded
    private AuditRecord auditRecord;

    public Role (final CreateRoleDTO createRoleDTO, final String initiatorUsername) {
        this.name = createRoleDTO.name();
        this.description = createRoleDTO.description();
        this.color = createRoleDTO.color();
        this.roleStatus = RoleStatus.ACTIVE;

        this.auditRecord = AuditRecord.builder()
            .insertionUser(initiatorUsername)
            .insertionDate(new Date())
            .build();
    }
}