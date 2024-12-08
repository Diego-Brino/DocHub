package com.dochub.api.services;

import com.dochub.api.dtos.flow.CreateFlowDTO;
import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.flow.UpdateFlowDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Activity;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.User;
import com.dochub.api.exceptions.*;
import com.dochub.api.repositories.FlowRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
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

    public Boolean hasFlowsAssignedToActivity (final Activity activity) {
        final List<Flow> flows = flowRepository
            .findByActivity(activity)
            .orElse(Collections.emptyList());

        return !flows.isEmpty();
    }
    
    public List<FlowResponseDTO> getAllFlowsInProgressAssignedToUser (final Integer userId) {
        final List<Flow> flows = flowRepository
            .findAssignedFlowsByUser(userId)
            .orElse(Collections.emptyList());

        return flows
            .stream()
            .map(FlowResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public Integer create (final UserRoleResponseDTO userRoles,
                           final User user,
                           final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                           final Function<Process, Boolean> isProcessFinishedFunc,
                           final CreateFlowDTO createFlowDTO,
                           final Process process, final Activity activity,
                           final BiConsumer<User, Flow> assignedUserToFlow) {
        Utils.checkPermission(userRoles, process.getGroup().getId(), Constants.CREATE_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(process);
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(process);

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        final Flow flow = new Flow(createFlowDTO, process, activity, userRoles.user().username());

        if (_hasProcessFlowsWithOrderOne(flow, flow.getProcess())) {
            throw new FlowWithOrderOneAlreadyRegisterException();
        }

        final Flow flowSync = flowRepository.save(flow);

        assignedUserToFlow.accept(user, flowSync);

        return flow.getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer flowId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final Function<Process, Boolean> isProcessFinishedFunc,
                        final UpdateFlowDTO updateFlowDTO,
                        final Activity activity) {
        final Flow flow = getById(flowId);

        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.EDIT_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(flow.getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

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
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final Function<Process, Boolean> isProcessFinishedFunc) {
        final Flow flow = getById(flowId);

        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.DELETE_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(flow.getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

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