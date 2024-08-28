package com.dochub.api.dtos.auth;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationResponseDTO (@NotBlank(message = Constants.AUTHENTICATION_TOKEN_IS_REQUIRED_MESSAGE) String token) {
}