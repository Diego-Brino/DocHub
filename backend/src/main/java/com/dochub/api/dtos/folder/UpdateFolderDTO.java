package com.dochub.api.dtos.folder;

import org.hibernate.validator.constraints.Length;

public record UpdateFolderDTO (
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    Integer parentFolderId
) {
}