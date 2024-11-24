package com.dochub.api.dtos.folder;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;

import java.util.List;

public record FolderContentsResponseDTO (
    List<ArchiveResponseDTO> archives,
    List<FolderResponseDTO> folders
) {
}