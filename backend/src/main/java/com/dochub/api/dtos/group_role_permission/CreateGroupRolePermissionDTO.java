package com.dochub.api.dtos.group_role_permission;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotNull;

public record CreateGroupRolePermissionDTO (
    @NotNull(message = Constants.ID_ROLE_IS_REQUIRED_MESSAGE)
    Integer idRole,

    @NotNull(message = Constants.ID_GROUP_PERMISSION_IS_REQUIRED_MESSAGE)
    Integer idGroupPermission,

    @NotNull(message = Constants.ID_GROUP_IS_REQUIRED_MESSAGE)
    Integer idGroup
) {
}