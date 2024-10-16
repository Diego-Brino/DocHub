package com.dochub.api.dtos.archive;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record UpdateArchiveDTO (
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    Integer folderId,

    MultipartFile file
) {
}