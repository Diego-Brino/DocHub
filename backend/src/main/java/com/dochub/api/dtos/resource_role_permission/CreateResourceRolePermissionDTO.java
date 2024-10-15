package com.dochub.api.dtos.resource_role_permission;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotNull;

public record CreateResourceRolePermissionDTO(
    @NotNull(message = Constants.ID_ROLE_IS_REQUIRED_MESSAGE)
    Integer idRole,

    @NotNull(message = Constants.ID_RESOURCE_PERMISSION_IS_REQUIRED_MESSAGE)
    Integer idResourcePermission,

    @NotNull(message = Constants.ID_RESOURCE_IS_REQUIRED_MESSAGE)
    Integer idResource
) {
}