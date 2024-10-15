package com.dochub.api.services;

import com.dochub.api.dtos.resource_permission.ResourcePermissionResponseDTO;
import com.dochub.api.entities.ResourcePermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourcePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourcePermissionService {
    private final ResourcePermissionRepository resourcePermissionRepository;

    public ResourcePermission getById (final Integer resourcePermissionId) {
        return resourcePermissionRepository
            .findById(resourcePermissionId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ResourcePermissionResponseDTO getDtoById (final Integer resourcePermissionId) {
        final ResourcePermission resourcePermission = getById(resourcePermissionId);

        return new ResourcePermissionResponseDTO(resourcePermission);
    }

    public List<ResourcePermissionResponseDTO> getAll () {
        final List<ResourcePermission> resourcePermissions = resourcePermissionRepository.findAll();

        return resourcePermissions
            .stream()
            .map(ResourcePermissionResponseDTO::new)
            .collect(Collectors.toList());
    }
}