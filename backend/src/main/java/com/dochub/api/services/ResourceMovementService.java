package com.dochub.api.services;

import com.dochub.api.dtos.resource_movement.ResourceMovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Resource;
import com.dochub.api.entities.resource_movement.ResourceMovement;
import com.dochub.api.entities.resource_movement.ResourceMovementPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.FlowInteractionNotAuthorizedException;
import com.dochub.api.repositories.ResourceMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
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

    public ResourceMovementPK create (final UserRoleResponseDTO userRoles,
                                      final BiFunction<Flow, Integer, Boolean> isUserAuthorizedFunc,
                                      final Movement movement, final Resource resource) {
        final Boolean isUserAuthorized = isUserAuthorizedFunc.apply(movement.getResponseFlow().getFlow(), userRoles.user().id());

        if (!isUserAuthorized) throw new FlowInteractionNotAuthorizedException();

        final ResourceMovementPK resourceMovementId = new ResourceMovementPK(movement.getId(), resource.getId());
        final ResourceMovement resourceMovement = new ResourceMovement(resourceMovementId, movement, resource, userRoles.user().username());

        return resourceMovementRepository.save(resourceMovement).getId();
    }
}