package com.dochub.api.dtos.folder;

import com.dochub.api.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

public record CreateFolderDTO (
    @NotBlank(message = Constants.NAME_IS_REQUIRED_MESSAGE)
    @Length(max = 128)
    String name,

    @Length(max = 256)
    String description,

    @NonNull
    Integer groupId,

    Integer parentFolderId
) {
}