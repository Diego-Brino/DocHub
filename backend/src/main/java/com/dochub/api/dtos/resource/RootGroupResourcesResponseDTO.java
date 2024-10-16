package com.dochub.api.dtos.resource;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.folder.FolderResponseDTO;

import java.util.List;

public record RootGroupResourcesResponseDTO (
    List<ArchiveResponseDTO> archives,
    List<FolderResponseDTO> folders
) {
}