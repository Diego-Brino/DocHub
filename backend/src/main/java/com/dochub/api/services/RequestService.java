package com.dochub.api.services;

import com.dochub.api.entities.Process;
import com.dochub.api.entities.Request;
import com.dochub.api.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public Boolean hasRequestInProgressAssignedToProcess (final Process process) {
        final List<Request> requests = requestRepository
            .findByProcessAndStatus_InProgress(process)
            .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Boolean hasRequestInProgressAssignedToService (final com.dochub.api.entities.Service service) {
        final List<Request> requests = requestRepository
            .findByProcess_ServiceAndStatus_InProgress(service)
            .orElse(Collections.emptyList());

        if (requests.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }
}