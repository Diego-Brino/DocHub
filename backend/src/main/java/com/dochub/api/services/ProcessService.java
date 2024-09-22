package com.dochub.api.services;

import com.dochub.api.entities.Group;
import com.dochub.api.entities.Process;
import com.dochub.api.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessRepository processRepository;

    public Boolean hasProcessesAssignedToGroup (final Group group) {
        final List<Process> processes = processRepository
            .findByGroup(group)
            .orElse(Collections.emptyList());

        if (processes.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }
}