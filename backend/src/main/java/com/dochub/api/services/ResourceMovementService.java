package com.dochub.api.services;

import com.dochub.api.dtos.resource_movement.ResourceMovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.resource_movement.ResourceMovement;
import com.dochub.api.entities.resource_movement.ResourceMovementPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.ResourceMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceMovementService {
    private final ResourceMovementRepository resourceMovementRepository;

    public ResourceMovement getById (final Integer movementId, final Integer resourceId) {
        final ResourceMovementPK resourceMovementId = new ResourceMovementPK(movementId, resourceId);

        return resourceMovementRepository
            .findById(resourceMovementId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ResourceMovementResponseDTO getDtoById (final Integer movementId, final Integer resourceId) {
        final ResourceMovement resourceMovement = getById(movementId, resourceId);

        return new ResourceMovementResponseDTO(resourceMovement);
    }

    public List<ResourceMovementResponseDTO> getAll () {
        final List<ResourceMovement> resourceMovements = resourceMovementRepository.findAll();

        return resourceMovements
            .stream()
            .map(ResourceMovementResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles,
                           final Function<Process, Boolean> isProcessFinishedFunc,
                           final Movement movement, final Resource resource) {
        return 1;
    }
}