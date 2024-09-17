package com.dochub.api.services;

import com.dochub.api.dtos.system_permission.SystemPermissionResponseDTO;
import com.dochub.api.entities.SystemPermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.SystemPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemPermissionService {
    private final SystemPermissionRepository systemPermissionRepository;

    public SystemPermissionResponseDTO getById (final Integer systemPermissionId) {
        final SystemPermission systemPermission = systemPermissionRepository
            .findById(systemPermissionId)
            .orElseThrow(EntityNotFoundByIdException::new);

        return new SystemPermissionResponseDTO(systemPermission);
    }

    public List<SystemPermissionResponseDTO> getAll () {
        final List<SystemPermission> systemPermissions = systemPermissionRepository.findAll();

        return systemPermissions
            .stream()
            .map(SystemPermissionResponseDTO::new)
            .collect(Collectors.toList());
    }
}