package com.dochub.api.dtos.group_role_permission;

import com.dochub.api.dtos.group.GroupResponseDTO;
import com.dochub.api.dtos.group_permission.GroupPermissionResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.GroupPermission;

import java.util.List;

public record GroupRolePermissionResponseDTO (
    GroupResponseDTO group,
    List<GroupPermissionResponseDTO> permissions
) {
    public GroupRolePermissionResponseDTO (final Group group, final List<GroupPermission> groupPermissions) {
        this(
            new GroupResponseDTO(group),
            groupPermissions
                .stream()
                .map(GroupPermissionResponseDTO::new)
                .toList()
        );
    }
}
