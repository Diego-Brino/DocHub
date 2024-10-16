package com.dochub.api.services;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.folder.FolderResponseDTO;
import com.dochub.api.dtos.resource.RootGroupResourcesResponseDTO;
import com.dochub.api.entities.Folder;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.User;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public Resource getById (final Integer resourceId) {
        return resourceRepository
            .findById(resourceId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public Boolean hasResourcesAssignedToGroup (final Group group) {
        final Integer qtdResources = resourceRepository.countResourceByGroup(group);

        if (Objects.equals(qtdResources, 0)) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public RootGroupResourcesResponseDTO getRootGroupResources (final User user, final Group group,
                                                                final TriFunction<Group, Folder, User, List<ArchiveResponseDTO>> getGroupArchivesByFolderFunc,
                                                                final TriFunction<Group, Folder, User, List<FolderResponseDTO>> getGroupFoldersByParentFolderFunc) {
        final List<ArchiveResponseDTO> archives = getGroupArchivesByFolderFunc.apply(group, null, user);
        final List<FolderResponseDTO> folders = getGroupFoldersByParentFolderFunc.apply(group, null, user);

        return new RootGroupResourcesResponseDTO(archives, folders);
    }
}