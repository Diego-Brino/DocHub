package com.dochub.api.dtos.system_role_permission;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotNull;

public record CreateSystemRolePermissionDTO (
    @NotNull(message = Constants.ID_ROLE_IS_REQUIRED_MESSAGE)
    Integer idRole,

    @NotNull(message = Constants.ID_SYSTEM_PERMISSION_IS_REQUIRED_MESSAGE)
    Integer idSystemPermission) {
}