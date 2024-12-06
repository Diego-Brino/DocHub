package com.dochub.api.services;

import com.dochub.api.dtos.request.RequestResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.Request;
import com.dochub.api.entities.User;
import com.dochub.api.enums.RequestStatus;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ProcessAlreadyFinishedException;
import com.dochub.api.repositories.RequestRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public Request getById (final Integer requestId) {
        return requestRepository
            .findById(requestId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public RequestResponseDTO getDtoById (final Integer requestId) {
        final Request request = getById(requestId);

        return new RequestResponseDTO(request);
    }

    public List<RequestResponseDTO> getAll () {
        final List<Request> requests = requestRepository.findAll();

        return requests
            .stream()
            .map(RequestResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Boolean hasRequestInProgressAssignedToService (final com.dochub.api.entities.Service service) {
        final List<Request> requests = requestRepository
                .findByProcess_ServiceAndStatus_InProgress(service)
                .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Boolean hasRequestInProgressAssignedToProcess (final Process process) {
        final List<Request> requests = requestRepository
            .findByProcessAndStatus_InProgress(process)
            .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Boolean hasRequestAssignedToService (final com.dochub.api.entities.Service service) {
        final List<Request> requests = requestRepository
            .findByProcess_Service(service)
            .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Boolean hasRequestAssignedToProcess (final Process process) {
        final List<Request> requests = requestRepository
            .findByProcess(process)
            .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Integer create (final UserRoleResponseDTO userRoles,
                           final Function<Process, Boolean> isProcessFinishedFunc,
                           final User user, final Process process) {
        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.CREATE_REQUEST_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(process);

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();

        final Request request = new Request(user, process, userRoles.user().username());

        return requestRepository.save(request).getId();
    }

    public void setRequestAsFinished (final Integer requestId) {
        final Request request = getById(requestId);

        request.setStatus(RequestStatus.FINISHED);

        request.getAuditRecord().setAlterationUser(Constants.SYSTEM_NAME);
        request.getAuditRecord().setAlterationDate(new Date());

        requestRepository.save(request);
    }
}