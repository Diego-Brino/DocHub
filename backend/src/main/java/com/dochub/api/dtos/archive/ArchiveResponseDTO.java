package com.dochub.api.dtos.archive;

import com.dochub.api.entities.Resource;

public record ArchiveResponseDTO (
    Integer id,
    String name,
    String description,
    Long length,
    String type,
    String S3Hash
) {
    public ArchiveResponseDTO (final Resource resource) {
        this (
            resource.getId(),
            resource.getName(),
            resource.getDescription(),
            resource.getArchive().getLength(),
            resource.getArchive().getType(),
            resource.getArchive().getS3Hash()
        );
    }
}