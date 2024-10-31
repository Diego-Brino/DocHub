package com.dochub.api.dtos.s3.object;

import lombok.NonNull;

public record DeleteObjectDTO (
    @NonNull
    String bucketName,

    @NonNull
    String objectName
) {
}