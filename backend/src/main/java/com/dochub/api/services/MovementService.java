package com.dochub.api.services;

import com.dochub.api.dtos.movement.MovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.*;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final MovementRepository movementRepository;

    public Movement getById (final Integer movementId) {
        return movementRepository
            .findById(movementId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public MovementResponseDTO getDtoById (final Integer movementId) {
        final Movement movement = getById(movementId);

        return new MovementResponseDTO(movement);
    }

    public List<MovementResponseDTO> getAll () {
        final List<Movement> movements = movementRepository.findAll();

        return movements
            .stream()
            .map(MovementResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles,
                           final Function<Process, Boolean> isProcessFinishedFunc,
                           final Consumer<Integer> setRequestAsFinishedFunc,
                           final Request request, final Flow flow, final Response response) {
        return 1;
    }
}