package com.dochub.api.dtos.user;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdatePasswordDTO (
    @NotBlank(message = Constants.OLD_PASSWORD_IS_REQUIRED_MESSAGE)
    @Length(max = 256)
    String oldPassword,

    @NotBlank(message = Constants.NEW_PASSWORD_IS_REQUIRED_MESSAGE)
    @Length(max = 256)
    String newPassword) {
}