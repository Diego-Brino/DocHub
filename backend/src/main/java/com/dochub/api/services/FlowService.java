package com.dochub.api.services;

import com.dochub.api.dtos.flow.CreateFlowDTO;
import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.flow.UpdateFlowDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Activity;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Process;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.FlowWithOrderOneAlreadyRegisterException;
import com.dochub.api.exceptions.ProcessAlreadyStartedException;
import com.dochub.api.exceptions.ProcessInProgressException;
import com.dochub.api.repositories.FlowRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowService {
    private final FlowRepository flowRepository;

    public Flow getById (final Integer flowId) {
        return flowRepository
            .findById(flowId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public FlowResponseDTO getDtoById (final Integer flowId) {
        final Flow flow = getById(flowId);

        return new FlowResponseDTO(flow);
    }

    public List<FlowResponseDTO> getAll () {
        final List<Flow> flows = flowRepository.findAll();

        return flows
            .stream()
            .map(FlowResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Integer create (final UserRoleResponseDTO userRoles,
                           final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                           final CreateFlowDTO createFlowDTO,
                           final Process process, final Activity activity) {
        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.CREATE_FLOW_PERMISSION);

        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(process);

        if (hasRequestAssignedToProcess) {
            throw new ProcessAlreadyStartedException();
        }

        final Flow flow = new Flow(createFlowDTO, process, activity, userRoles.user().username());

        if (_hasProcessFlowsWithOrderOne(flow, flow.getProcess())) {
            throw new FlowWithOrderOneAlreadyRegisterException();
        }

        return flowRepository.save(flow).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer flowId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final UpdateFlowDTO updateFlowDTO,
                        final Activity activity) {
        final Flow flow = getById(flowId);

        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.EDIT_FLOW_PERMISSION);

        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (hasRequestAssignedToProcess) {
            throw new ProcessInProgressException();
        }

        _updateOrderIfPresent(flow, updateFlowDTO.order());
        _updateTimeIfPresent(flow, updateFlowDTO.time());
        _updateLimitDateIfPresent(flow, updateFlowDTO.limitDate());
        _updateActivityIfPresent(flow, activity);

        if (_hasProcessFlowsWithOrderOne(flow, flow.getProcess())) {
            throw new FlowWithOrderOneAlreadyRegisterException();
        }

        _logAuditForChange(flow, userRoles.user().username());

        flowRepository.save(flow);
    }

    public void delete (final UserRoleResponseDTO userRoles,
                        final Integer flowId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc) {
        final Flow flow = getById(flowId);

        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.DELETE_FLOW_PERMISSION);

        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (hasRequestAssignedToProcess) {
            throw new ProcessInProgressException();
        }

        flowRepository.delete(flow);
    }

    private Boolean _hasProcessFlowsWithOrderOne (final Flow flow, final Process process) {
        for (Flow processFlow : process.getFlows()) {
            if (Objects.equals(processFlow.getOrder(), 1) && !Objects.equals(flow, processFlow) && Objects.equals(flow.getOrder(), 1)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    private void _updateOrderIfPresent (final Flow flow, final Integer order) {
        if (Objects.nonNull(order)) {
            flow.setOrder(order);
        }
    }

    private void _updateTimeIfPresent (final Flow flow, final Integer time) {
        if (Objects.nonNull(time)) {
            flow.setTime(time);
        }
    }

    private void _updateLimitDateIfPresent (final Flow flow, final Date limitDate) {
        if (Objects.nonNull(limitDate)) {
            flow.setLimitDate(limitDate);
        }
    }

    private void _updateActivityIfPresent (final Flow flow, final Activity activity) {
        if (Objects.nonNull(activity)) {
            flow.setActivity(activity);
        }
    }

    private void _logAuditForChange (final Flow flow, final String actor) {
        flow.getAuditRecord().setAlterationUser(actor);
        flow.getAuditRecord().setAlterationDate(new Date());
    }
}