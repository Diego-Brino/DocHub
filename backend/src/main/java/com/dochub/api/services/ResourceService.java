package com.dochub.api.services;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.folder.FolderResponseDTO;
import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.dtos.resource.RootGroupResourcesResponseDTO;
import com.dochub.api.entities.*;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public Resource getById (final Integer resourceId) {
        return resourceRepository
            .findById(resourceId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public List<ResourceResponseDTO> getAllGroupResources (final Group group,
                                                           final Function<Group, List<Archive>> getArchivesByGroupFunc,
                                                           final Function<Group, List<Folder>> getFoldersByGroupFunc) {
        final List<Archive> archives = getArchivesByGroupFunc.apply(group);
        final List<Folder> folders = getFoldersByGroupFunc.apply(group);

        final List<ResourceResponseDTO> resources = new ArrayList<>();

        archives.forEach(archive -> resources.add(new ResourceResponseDTO(archive.getResource())));
        folders.forEach(folder -> resources.add(new ResourceResponseDTO(folder.getResource())));

        return resources;
    }

    public RootGroupResourcesResponseDTO getRootGroupResources (final User user, final Group group,
                                                                final TriFunction<Group, Folder, User, List<ArchiveResponseDTO>> getGroupArchivesByFolderFunc,
                                                                final TriFunction<Group, Folder, User, List<FolderResponseDTO>> getGroupFoldersByParentFolderFunc) {
        final List<ArchiveResponseDTO> archives = getGroupArchivesByFolderFunc.apply(group, null, user);
        final List<FolderResponseDTO> folders = getGroupFoldersByParentFolderFunc.apply(group, null, user);

        return new RootGroupResourcesResponseDTO(archives, folders);
    }
}