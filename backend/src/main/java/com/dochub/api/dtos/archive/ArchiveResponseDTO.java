package com.dochub.api.dtos.archive;

import com.dochub.api.entities.Resource;

public record ArchiveResponseDTO (
    String S3Hash,
    Integer id,
    String name,
    String description,
    String type,
    Long length
) {
    public ArchiveResponseDTO (final Resource resource) {
        this (
            resource.getArchive().getS3Hash(),
            resource.getId(),
            resource.getName(),
            resource.getDescription(),
            resource.getArchive().getType(),
            resource.getArchive().getLength()
        );
    }
}