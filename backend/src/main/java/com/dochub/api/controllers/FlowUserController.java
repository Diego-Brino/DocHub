package com.dochub.api.controllers;

import com.dochub.api.dtos.flow_user.CreateFlowUserDTO;
import com.dochub.api.dtos.flow_user.FlowUserResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.User;
import com.dochub.api.entities.flow_user.FlowUserPK;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flow-users")
@RequiredArgsConstructor
public class FlowUserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RequestService requestService;
    private final FlowService flowService;
    private final ProcessService processService;
    private final FlowUserService flowUserService;

    @GetMapping
    public ResponseEntity<List<FlowUserResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(flowUserService.getAll());
    }

    @GetMapping("/{userId}/{flowId}")
    public ResponseEntity<FlowUserResponseDTO> getOne (@PathVariable("userId") @NonNull final Integer userId,
                                                       @PathVariable("flowId") @NonNull final Integer flowId) {
        return ResponseEntity
            .ok()
            .body(flowUserService.getDtoById(userId, flowId));
    }

    @PostMapping
    public ResponseEntity<FlowUserPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                              @RequestBody @Valid @NonNull final CreateFlowUserDTO createFlowUserDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final User userFlow = userService.getById(createFlowUserDTO.userId());
        final Flow flow = flowService.getById(createFlowUserDTO.flowId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(flowUserService.create(
                userRoles,
                requestService::hasRequestAssignedToProcess,
                processService::isProcessFinished,
                userFlow,
                flow
            ));
    }

    @DeleteMapping("/{userId}/{flowId}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("userId") @NonNull final Integer userId,
                                        @PathVariable("flowId") @NonNull final Integer flowId) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        flowUserService.delete(
            userRoles,
            userId,
            flowId,
            requestService::hasRequestAssignedToProcess,
            processService::isProcessFinished
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}