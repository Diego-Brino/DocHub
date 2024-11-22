package com.dochub.api.dtos.activity;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public record CreateActivityDTO (
    @NotBlank(message = Constants.DESCRIPTION_IS_REQUIRED_MESSAGE)
    String description
) {
}