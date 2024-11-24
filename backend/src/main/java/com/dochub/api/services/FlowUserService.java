package com.dochub.api.services;

import com.dochub.api.dtos.flow_user.FlowUserResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.User;
import com.dochub.api.entities.flow_user.FlowUser;
import com.dochub.api.entities.flow_user.FlowUserPK;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.exceptions.ProcessAlreadyFinishedException;
import com.dochub.api.exceptions.ProcessAlreadyStartedException;
import com.dochub.api.repositories.FlowUserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlowUserService {
    private final FlowUserRepository flowUserRepository;

    public FlowUser getById (final Integer userId, final Integer flowId) {
        final FlowUserPK flowUserPK = new FlowUserPK(userId, flowId);

        return flowUserRepository
            .findById(flowUserPK)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public FlowUserResponseDTO getDtoById (final Integer userId, final Integer flowId) {
        final FlowUser flowUser = getById(userId, flowId);

        return new FlowUserResponseDTO(flowUser);
    }

    public List<FlowUserResponseDTO> getAll () {
        final List<FlowUser> flowUsers = flowUserRepository.findAll();

        return flowUsers
            .stream()
            .map(FlowUserResponseDTO::new)
            .collect(Collectors.toList());
    }

    public FlowUserPK create (final UserRoleResponseDTO userRoles,
                              final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                              final Function<Process, Boolean> isProcessFinishedFunc,
                              final User user, final Flow flow) {
        Utils.checkPermission(userRoles, flow.getProcess().getGroup().getId(), Constants.CREATE_FLOW_USER_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(flow.getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flow.getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        final FlowUser flowUser = new FlowUser(flow, user, userRoles.user().username());

        return flowUserRepository.save(flowUser).getId();
    }

    public void delete (final UserRoleResponseDTO userRoles,
                        final Integer userId, final Integer flowId,
                        final Function<Process, Boolean> hasRequestAssignedToProcessFunc,
                        final Function<Process, Boolean> isProcessFinishedFunc) {
        final FlowUser flowUser = getById(userId, flowId);

        Utils.checkPermission(userRoles, flowUser.getFlow().getProcess().getGroup().getId(), Constants.DELETE_FLOW_USER_PERMISSION);

        final Boolean isProcessFinished = isProcessFinishedFunc.apply(flowUser.getFlow().getProcess());
        final Boolean hasRequestAssignedToProcess = hasRequestAssignedToProcessFunc.apply(flowUser.getFlow().getProcess());

        if (isProcessFinished) throw new ProcessAlreadyFinishedException();
        if (hasRequestAssignedToProcess) throw new ProcessAlreadyStartedException();

        flowUserRepository.delete(flowUser);
    }
}