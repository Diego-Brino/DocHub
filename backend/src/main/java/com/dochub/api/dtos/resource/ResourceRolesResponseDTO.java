package com.dochub.api.dtos.resource;

import com.dochub.api.dtos.role.ResourceRoleResponseDTO;

import java.util.List;

public record ResourceRolesResponseDTO(
    ResourceResponseDTO resource,
    List<ResourceRoleResponseDTO> roles
) {
}