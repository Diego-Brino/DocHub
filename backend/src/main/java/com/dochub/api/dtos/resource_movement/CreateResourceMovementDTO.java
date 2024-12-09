package com.dochub.api.dtos.resource_movement;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

public record CreateResourceMovementDTO (
    @NonNull
    Integer movementId,

    @NotBlank(message = Constants.HASH_S3_IS_REQUIRED_MESSAGE)
    String hashS3,

    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    @NotBlank(message = Constants.CONTENT_TYPE_IS_REQUIRED_MESSAGE)
    String contentType,

    @NonNull
    Long length
) {
}
