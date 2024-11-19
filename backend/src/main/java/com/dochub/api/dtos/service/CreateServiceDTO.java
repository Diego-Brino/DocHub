package com.dochub.api.dtos.service;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public record CreateServiceDTO (
    @NotBlank(message = Constants.DESCRIPTION_IS_REQUIRED_MESSAGE)
    String description
) {
}