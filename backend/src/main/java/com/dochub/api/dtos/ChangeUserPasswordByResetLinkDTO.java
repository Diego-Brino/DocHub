package com.dochub.api.dtos;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordByResetLinkDTO (
    @NotBlank(message = Constants.AUTHENTICATION_TOKEN_IS_REQUIRED_MESSAGE)
    String token,

    @NotBlank(message = Constants.PASSWORD_IS_REQUIRED_MESSAGE)
    String newPassword
) {
}