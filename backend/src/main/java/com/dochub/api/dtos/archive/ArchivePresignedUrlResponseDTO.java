package com.dochub.api.dtos.archive;

public record ArchivePresignedUrlResponseDTO(
    String url,
    String hashS3
) {
}