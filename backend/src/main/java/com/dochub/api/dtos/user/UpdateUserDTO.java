package com.dochub.api.dtos.user;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDTO (
    @Length(max = 256)
    String name,

    @Length(max = 256)
    String password,

    @Email(message = Constants.INVALID_EMAIL_MESSAGE)
    @Length(max = 128)
    String email,

    @Length(max = 256)
    String username,

    MultipartFile avatar) {
}