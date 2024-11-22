package com.dochub.api.dtos.response;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public record CreateResponseDTO (
    @NotBlank(message = Constants.DESCRIPTION_IS_REQUIRED_MESSAGE)
    String description
) {
}