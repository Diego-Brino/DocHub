package com.dochub.api.dtos.user_roles;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotNull;

public record CreateUserRoleDTO (
    @NotNull(message = Constants.ID_USER_IS_REQUIRED_MESSAGE)
    Integer idUser,

    @NotNull(message = Constants.ID_ROLE_IS_REQUIRED_MESSAGE)
    Integer idRole) {
}