package com.dochub.api.dtos.user;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record ProfileCreateUserDTO (
    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    @Length(max = 256)
    String name,

    @NotBlank(message = Constants.EMAIL_IS_REQUIRED_MESSAGE)
    @Email(message = Constants.INVALID_EMAIL_MESSAGE)
    @Length(max = 128)
    String email,

    @NotBlank(message = Constants.USERNAME_IS_REQUIRED_MESSAGE)
    @Length(max = 256)
    String username,

    MultipartFile avatar
) {
}