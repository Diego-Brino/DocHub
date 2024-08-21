package com.dochub.api.dtos.role;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateRoleDTO (
    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    @NotBlank(message = Constants.COLOR_IS_REQUIRED_MESSAGE)
    @Length(max = 32)
    String color) {
}