package com.dochub.api.services;

import com.dochub.api.dtos.archive.ArchiveResponseDTO;
import com.dochub.api.dtos.folder.FolderResponseDTO;
import com.dochub.api.dtos.resource.ResourceResponseDTO;
import com.dochub.api.dtos.resource.ResourceRolePermissionDTO;
import com.dochub.api.dtos.resource.ResourceRolesResponseDTO;
import com.dochub.api.dtos.resource.RootGroupResourcesResponseDTO;
import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.dtos.role.ResourceRoleResponseDTO;
import com.dochub.api.dtos.role.RoleResponseDTO;
import com.dochub.api.entities.*;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public ResourceRolesResponseDTO getResourceRoles (final Integer resourceId) {
        final List<ResourceRolePermissionDTO> resourceRolesPermissions = resourceRepository
            .findResourceRolesPermissionsByResourceId(resourceId)
            .orElse(Collections.emptyList());

        if (resourceRolesPermissions.isEmpty()) {
            return new ResourceRolesResponseDTO(new ResourceResponseDTO(getById(resourceId)), new ArrayList<>());
        }

        final ResourceResponseDTO resourceDTO = extractResource(resourceRolesPermissions);
        final List<ResourceRoleResponseDTO> roles = groupRolesWithPermissions(resourceRolesPermissions);

        return new ResourceRolesResponseDTO(resourceDTO, roles);
    }

    private ResourceResponseDTO extractResource (final List<ResourceRolePermissionDTO> resourceRolesPermissions) {
        return resourceRolesPermissions.getFirst().resource();
    }

    private List<ResourceRoleResponseDTO> groupRolesWithPermissions (final List<ResourceRolePermissionDTO> resourceRolesPermissions) {
        Map<Integer, ResourceRoleResponseDTO> roleMap = new LinkedHashMap<>();

        for (ResourceRolePermissionDTO dto : resourceRolesPermissions) {
            final RoleResponseDTO roleDTO = dto.role();
            final ResourcePermissionResponseDTO permissionDTO = dto.permission();

            final ResourceRoleResponseDTO roleResponse = roleMap.computeIfAbsent(
                roleDTO.id(),
                id -> createRoleResponse(roleDTO)
            );

            addPermissionToRole(roleResponse, permissionDTO);
        }

        return new ArrayList<>(roleMap.values());
    }

    private ResourceRoleResponseDTO createRoleResponse (final RoleResponseDTO role) {
        return new ResourceRoleResponseDTO(
            role.id(),
            role.name(),
            role.description(),
            role.color(),
            role.status(),
            new ArrayList<>()
        );
    }

    private void addPermissionToRole (final ResourceRoleResponseDTO roleResponse, final ResourcePermissionResponseDTO permissionDTO) {
        if (permissionDTO != null && !roleResponse.permissions().contains(permissionDTO)) {
            roleResponse.permissions().add(permissionDTO);
        }
    }
}