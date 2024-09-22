package com.dochub.api.dtos.group;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record CreateGroupDTO (
    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    MultipartFile avatar
) {
}