package com.dochub.api.dtos;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record RegisterUserDTO (
    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    String name,

    @NotBlank(message = Constants.PASSWORD_IS_REQUIRED_MESSAGE)
    String password,

    @NotBlank(message = Constants.EMAIL_IS_REQUIRED_MESSAGE)
    @Email(message = Constants.INVALID_EMAIL_MESSAGE)
    String email,

    @NotBlank(message = Constants.USERNAME_IS_REQUIRED_MESSAGE)
    String username,

    MultipartFile avatar) {
}