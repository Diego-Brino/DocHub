package com.dochub.api.dtos.group;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record UpdateGroupDTO(
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    MultipartFile avatar
) {
}
