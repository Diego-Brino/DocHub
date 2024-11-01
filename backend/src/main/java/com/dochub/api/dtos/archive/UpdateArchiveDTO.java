package com.dochub.api.dtos.archive;

import org.hibernate.validator.constraints.Length;

public record UpdateArchiveDTO (
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    Integer folderId,

    String contentType,
    
    Long length) {
}