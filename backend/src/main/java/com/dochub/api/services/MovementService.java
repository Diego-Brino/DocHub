package com.dochub.api.services;

import com.dochub.api.dtos.movement.MovementResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Movement;
import com.dochub.api.entities.Request;
import com.dochub.api.entities.flow_user.FlowUser;
import com.dochub.api.entities.response_flow.ResponseFlow;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.FlowInteractionNotAuthorizedException;
import com.dochub.api.exceptions.RequestAlreadyFinishedException;
import com.dochub.api.repositories.MovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    public Integer getOrderForCreateMovement (final Integer requestId) {
        final List<Movement> movement = movementRepository
            .findMovementByRequest_IdOrderByOrderDesc(requestId)
            .orElse(Collections.emptyList());

        if (movement.isEmpty()) {
            return 1;
        }

        return movement.getFirst().getOrder() + 1;
    }

    @Transactional
    public Integer create (final UserRoleResponseDTO userRoles,
                           final Function<Integer, Boolean> isRequestFinishedFunc,
                           final Consumer<Integer> setRequestAsFinishedFunc,
                           final Request request, final ResponseFlow responseFlow) {
        final Boolean isRequestFinished = isRequestFinishedFunc.apply(request.getId());

        if (isRequestFinished) throw new RequestAlreadyFinishedException();
        if (!_isUserAuthorized(responseFlow.getFlow(), userRoles.user().id())) throw new FlowInteractionNotAuthorizedException();

        final Integer order = getOrderForCreateMovement(request.getId());
        final Movement movement = new Movement(request, responseFlow, order, userRoles.user().username());

        if (Objects.isNull(responseFlow.getDestinationFlow())) setRequestAsFinishedFunc.accept(request.getId());

        return movementRepository.save(movement).getId();
    }

    private Boolean _isUserAuthorized (final Flow flow, final Integer userId) {
        for (FlowUser flowUser : flow.getFlowUsers()) {
            if (Objects.equals(flowUser.getUser().getId(), userId)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
}