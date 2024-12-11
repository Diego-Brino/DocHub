package com.dochub.api.services;

import com.dochub.api.dtos.process.ProcessResponseDTO;
import com.dochub.api.dtos.process.UpdateEndDateDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Group;
import com.dochub.api.entities.Process;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ProcessHasRequestAssignedException;
import com.dochub.api.exceptions.ProcessInProgressException;
import com.dochub.api.repositories.ProcessRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessRepository processRepository;

    public Process getById (final Integer processId) {
        return processRepository
            .findById(processId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ProcessResponseDTO getDtoById (final Integer processId) {
        final Process process = getById(processId);

        return new ProcessResponseDTO(process);
    }

    public List<ProcessResponseDTO> getAll () {
        final List<Process> processes = processRepository.findAll();

        return processes
            .stream()
            .map(ProcessResponseDTO::new)
            .collect(Collectors.toList());
    }

    public List<ProcessResponseDTO> getAllByGroup (final Group group) {
        final List<Process> processes = processRepository
            .findByGroup(group)
            .orElse(Collections.emptyList());

        return processes
            .stream()
            .map(ProcessResponseDTO::new)
            .collect(Collectors.toList());
    }

    public List<ProcessResponseDTO> getAllByService (final com.dochub.api.entities.Service service) {
        final List<Process> processes = processRepository
            .findByService(service)
            .orElse(Collections.emptyList());

        return processes
            .stream()
            .map(ProcessResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Boolean isProcessFinished (final Process process) {
        if (Objects.isNull(process.getEndDate())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Integer create (final UserRoleResponseDTO userRoles,
                           final com.dochub.api.entities.Service service, final Group group) {
        Utils.checkPermission(userRoles, group.getId(), Constants.CREATE_PROCESS_PERMISSION);

        final Process process = new Process(service, group, userRoles.user().username());

        return processRepository.save(process).getId();
    }

    public void updateEndDate (final UserRoleResponseDTO userRoles,
                               final Integer processId,
                               final Function<Process, Boolean> hasRequestInProgressAssignedToProcessFunc,
                               final UpdateEndDateDTO updateEndDateDTO) {
        final Process process = getById(processId);

        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.EDIT_PROCESS_PERMISSION);

        final Boolean hasRequestInProgressAssignedToProcess = hasRequestInProgressAssignedToProcessFunc.apply(process);

        if (hasRequestInProgressAssignedToProcess) {
            throw new ProcessInProgressException();
        }

        _updateEndDateIfPresent(process, updateEndDateDTO.endDate());

        _logAuditForChange(process, userRoles.user().username());

        processRepository.save(process);
    }

    public void putProcessInProgress (final UserRoleResponseDTO userRoles,
                                      final Integer processId) {
        final Process process = getById(processId);

        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.EDIT_PROCESS_PERMISSION);

        process.setEndDate(null);

        _logAuditForChange(process, userRoles.user().username());

        processRepository.save(process);
    }

    public void delete (final UserRoleResponseDTO userRoles,
                        final Integer processId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc) {
        final Process process = getById(processId);

        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.DELETE_PROCESS_PERMISSION);

        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(process);

        if (hasRequestAssignedToProcess) {
            throw new ProcessHasRequestAssignedException();
        }

        processRepository.delete(process);
    }

    public void deleteAllProcessAssignedToGroup (final Group group) {
        final List<Process> processes = processRepository
            .findByGroup(group)
            .orElse(Collections.emptyList());

        processRepository.deleteAll(processes);
    }

    private void _updateEndDateIfPresent (final Process process, final Date endDate) {
        if (Objects.nonNull(endDate)) {
            process.setEndDate(endDate);
        }
    }

    private void _logAuditForChange (final Process process, final String actor) {
        process.getAuditRecord().setAlterationUser(actor);
        process.getAuditRecord().setAlterationDate(new Date());
    }
}