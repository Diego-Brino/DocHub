package com.dochub.api.services;

import com.dochub.api.dtos.group_permission.GroupPermissionResponseDTO;
import com.dochub.api.entities.GroupPermission;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.EntityNotFoundException;
import com.dochub.api.repositories.GroupPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupPermissionService {
    private final GroupPermissionRepository groupPermissionRepository;

    public GroupPermissionResponseDTO getById (final Integer groupPermissionId) {
        final GroupPermission groupPermission = groupPermissionRepository
            .findById(groupPermissionId)
            .orElseThrow(EntityNotFoundByIdException::new);

        return new GroupPermissionResponseDTO(groupPermission);
    }

    public GroupPermissionResponseDTO getByDescription (final String groupPermissionDescription) {
        final GroupPermission groupPermission = groupPermissionRepository
            .findByDescriptionEqualsIgnoreCase(groupPermissionDescription)
            .orElseThrow(EntityNotFoundException::new);

        return new GroupPermissionResponseDTO(groupPermission);
    }

    public List<GroupPermissionResponseDTO> getAll () {
        final List<GroupPermission> groupPermissions = groupPermissionRepository.findAll();

        return groupPermissions
            .stream()
            .map(GroupPermissionResponseDTO::new)
            .collect(Collectors.toList());
    }
}