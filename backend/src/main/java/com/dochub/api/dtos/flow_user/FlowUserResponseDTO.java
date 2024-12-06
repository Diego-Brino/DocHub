package com.dochub.api.dtos.flow_user;

import com.dochub.api.dtos.flow.FlowUserFlowResponseDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entities.flow_user.FlowUser;

public record FlowUserResponseDTO(
    UserResponseDTO user,
    FlowUserFlowResponseDTO flow
) {
    public FlowUserResponseDTO(final FlowUser flowUser) {
        this (
            new UserResponseDTO(flowUser.getUser()),
            new FlowUserFlowResponseDTO(flowUser.getFlow())
        );
    }
}