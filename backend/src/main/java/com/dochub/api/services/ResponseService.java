package com.dochub.api.services;

import com.dochub.api.dtos.response.CreateResponseDTO;
import com.dochub.api.dtos.response.ResponseResponseDTO;
import com.dochub.api.dtos.response.UpdateResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Response;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ResponseInUseException;
import com.dochub.api.repositories.ResponseRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;

    public Response getById (final Integer responseId) {
        return responseRepository
            .findById(responseId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ResponseResponseDTO getDtoById (final Integer responseId) {
        final Response response = getById(responseId);

        return new ResponseResponseDTO(response);
    }

    public List<ResponseResponseDTO> getAll () {
        final List<Response> responses = responseRepository.findAll();

        return responses
            .stream()
            .map(ResponseResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles, final CreateResponseDTO createResponseDTO) {
        Utils.checkPermission(userRoles, Constants.CREATE_RESPONSE_PERMISSION);

        final Response response = new Response(createResponseDTO, userRoles.user().username());

        return responseRepository.save(response).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer responseId, final UpdateResponseDTO updateResponseDTO,
                        final Function<Response, Boolean> hasResponseFlowsAssignedToResponseFunc) {
        Utils.checkPermission(userRoles, Constants.EDIT_RESPONSE_PERMISSION);

        final Response response = getById(responseId);
        final Boolean hasResponseFlowsAssignedToResponse = hasResponseFlowsAssignedToResponseFunc.apply(response);

        if (hasResponseFlowsAssignedToResponse) {
            throw new ResponseInUseException();
        }

        Utils.updateFieldIfPresent(updateResponseDTO.description(), response::setDescription);

        _logAuditForChange(response, userRoles.user().username());

        responseRepository.save(response);
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer responseId,
                        final Function<Response, Boolean> hasResponseFlowsAssignedToResponseFunc) {
        Utils.checkPermission(userRoles, Constants.DELETE_RESPONSE_PERMISSION);

        final Response response = getById(responseId);
        final Boolean hasResponseFlowsAssignedToResponse = hasResponseFlowsAssignedToResponseFunc.apply(response);

        if (hasResponseFlowsAssignedToResponse) {
            throw new ResponseInUseException();
        }

        responseRepository.delete(response);
    }

    private void _logAuditForChange (final Response response, final String actor) {
        response.getAuditRecord().setInsertionUser(actor);
        response.getAuditRecord().setInsertionDate(new Date());
    }
}