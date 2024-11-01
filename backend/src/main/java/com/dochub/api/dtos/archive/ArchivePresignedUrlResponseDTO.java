package com.dochub.api.dtos.archive;

public record ArchivePresignedUrlResponseDTO(
    String hashS3,
    String url
) {
}