package com.dochub.api.dtos;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO (
    @NotBlank(message = Constants.EMAIL_IS_REQUIRED_MESSAGE)
    @Email(message = Constants.INVALID_EMAIL_MESSAGE)
    String email,

    @NotBlank(message = Constants.EMAIL_IS_REQUIRED_MESSAGE)
    String password) {
}