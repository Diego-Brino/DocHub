package com.dochub.api.dtos.archive;

public record ArchiveS3ResponseDTO (
    String contentType,
    byte[] file
) {
}
