package com.dochub.api.dtos.s3.object;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public record CreateObjectDTO (
    @NonNull
    String bucketName,

    @NonNull
    MultipartFile file
) {
}