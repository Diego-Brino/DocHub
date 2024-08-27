package com.dochub.api.dtos.recovery_password;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public record RecoveryPasswordDTO(
    @NotBlank(message = Constants.AUTHENTICATION_TOKEN_IS_REQUIRED_MESSAGE)
    String token,

    @NotBlank(message = Constants.NEW_PASSWORD_IS_REQUIRED_MESSAGE)
    String newPassword
) {
}