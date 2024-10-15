package com.dochub.api.entities;

import com.dochub.api.converters.RoleStatusConverter;
import com.dochub.api.dtos.role.CreateRoleDTO;
import com.dochub.api.entities.group_role_permission.GroupRolePermission;
import com.dochub.api.entities.resource_role_permission.ResourceRolePermission;
import com.dochub.api.entities.system_role_permission.SystemRolePermission;
import com.dochub.api.entities.user_role.UserRole;
import com.dochub.api.enums.RoleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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

    @OneToMany(mappedBy = "role", cascade = { CascadeType.REMOVE })
    private List<GroupRolePermission> groupRolePermissions;

    @OneToMany(mappedBy = "role", cascade = { CascadeType.REMOVE })
    private List<ResourceRolePermission> resourceRolePermissions;

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

    public Map<Group, List<GroupPermission>> getGroupPermissionsGroupedByGroup () {
        if (!groupRolePermissions.isEmpty()) {
            final Map<Group, List<GroupPermission>> map = new HashMap<>();

            groupRolePermissions.forEach(
                grp -> map.computeIfAbsent(grp.getGroup(), gp -> new ArrayList<>()).add(grp.getGroupPermission())
            );

            return map;
        }

        return Collections.emptyMap();
    }

    public Map<Resource, List<ResourcePermission>> getResourcePermissionsGroupedByResource () {
        if (!resourceRolePermissions.isEmpty()) {
            final Map<Resource, List<ResourcePermission>> map = new HashMap<>();

            resourceRolePermissions.forEach(
                rsp -> map.computeIfAbsent(rsp.getResource(), r -> new ArrayList<>()).add(rsp.getResourcePermission())
            );

            return map;
        }

        return Collections.emptyMap();
    }
}