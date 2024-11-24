package com.dochub.api.services;

import com.dochub.api.dtos.response_flow.ResponseFlowResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.Response;
import com.dochub.api.entities.response_flow.ResponseFlow;
import com.dochub.api.entities.response_flow.ResponseFlowPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ProcessAlreadyFinishedException;
import com.dochub.api.exceptions.ProcessAlreadyStartedException;
import com.dochub.api.repositories.ResponseFlowRepository;
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
public class ResponseFlowService {
    private final ResponseFlowRepository responseFlowRepository;

    public ResponseFlow getById (final Integer flowId, final Integer responseId) {
        final ResponseFlowPK responseFlowPK = new ResponseFlowPK(flowId, responseId);

        return responseFlowRepository
            .findById(responseFlowPK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public ResponseFlowResponseDTO getDtoById (final Integer flowId, final Integer responseId) {
        final ResponseFlow responseFlow = getById(flowId, responseId);

        return new ResponseFlowResponseDTO(responseFlow);
    }

    public List<ResponseFlowResponseDTO> getAll () {
        final List<ResponseFlow> responseFlows = responseFlowRepository.findAll();

        return responseFlows
            .stream()
            .map(ResponseFlowResponseDTO::new)
            .collect(Collectors.toList());
    }

    public Boolean hasResponseFlowsAssignedToResponse (final Response response) {
        final List<ResponseFlow> responseFlows = responseFlowRepository
            .findByResponse(response)
            .orElse(Collections.emptyList());

        return !responseFlows.isEmpty();
    }

    public ResponseFlowPK create (final UserRoleResponseDTO userRoles,
                                  final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                                  final Function<Process, Boolean> isProcessFinishedFunc,
                                  final Flow flow, final Response response, final Flow destinationFlow) {
        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.CREATE_RESPONSE_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(flow.getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        final ResponseFlow responseFlow = new ResponseFlow(flow, response, destinationFlow, userRoles.user().username());

        return responseFlowRepository.save(responseFlow).getId();
    }

    public void update (final UserRoleResponseDTO userRoles,
                        final Integer flowId, final Integer responseId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final Function<Process, Boolean> isProcessFinishedFunc,
                        final Flow destinationFlow) {
        final ResponseFlow responseFlow = getById(flowId, responseId);

        Utils.checkPermission(userRoles, responseFlow.getFlow().getProcess().getGroup().getId(), Constants.EDIT_RESPONSE_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(responseFlow.getFlow().getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(responseFlow.getFlow().getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        _updateDestinationFlowIfPresent(responseFlow, destinationFlow);

        _logAuditForChange(responseFlow, userRoles.user().username());

        responseFlowRepository.save(responseFlow);
    }

    public void delete (final UserRoleResponseDTO userRoles,
                        final Integer flowId, final Integer responseId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final Function<Process, Boolean> isProcessFinishedFunc) {
        final ResponseFlow responseFlow = getById(flowId, responseId);

        Utils.checkPermission(userRoles, responseFlow.getFlow().getProcess().getGroup().getId(), Constants.DELETE_RESPONSE_FLOW_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(responseFlow.getFlow().getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(responseFlow.getFlow().getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        responseFlowRepository.delete(responseFlow);
    }

    private void _updateDestinationFlowIfPresent (final ResponseFlow responseFlow, final Flow destinationFlow) {
        if (Objects.nonNull(destinationFlow)) {
            responseFlow.setDestinationFlow(destinationFlow);
        }
    }

    private void _logAuditForChange (final ResponseFlow responseFlow, final String actor) {
        responseFlow.getAuditRecord().setAlterationUser(actor);
        responseFlow.getAuditRecord().setAlterationDate(new Date());
    }
}