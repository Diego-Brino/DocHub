package com.dochub.api.controllers;

import com.dochub.api.dtos.response_flow.CreateResponseFlowDTO;
import com.dochub.api.dtos.response_flow.ResponseFlowResponseDTO;
import com.dochub.api.dtos.response_flow.UpdateResponseFlowDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Flow;
import com.dochub.api.entities.Response;
import com.dochub.api.entities.User;
import com.dochub.api.entities.response_flow.ResponseFlowPK;
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
import java.util.Objects;

@RestController
@RequestMapping("/response-flows")
@RequiredArgsConstructor
public class ResponseFlowController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RequestService requestService;
    private final FlowService flowService;
    private final ResponseService responseService;
    private final ResponseFlowService responseFlowService;
    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<List<ResponseFlowResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(responseFlowService.getAll());
    }

    @GetMapping("/{idFlow}/{idResponse}")
    public ResponseEntity<ResponseFlowResponseDTO> getOne (@PathVariable("idFlow") @NonNull final Integer idFlow,
                                                           @PathVariable("idResponse") @NonNull final Integer idResponse) {
        return ResponseEntity
            .ok()
            .body(responseFlowService.getDtoById(idFlow, idResponse));
    }

    @PostMapping
    public ResponseEntity<ResponseFlowPK> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                                  @RequestBody @Valid @NonNull final CreateResponseFlowDTO createResponseFlowDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Flow flow = flowService.getById(createResponseFlowDTO.flowId());
        final Response response = responseService.getById(createResponseFlowDTO.responseId());
        final Flow destinationFlow = Objects.nonNull(createResponseFlowDTO.destinationFlowId()) ? flowService.getById(createResponseFlowDTO.destinationFlowId()) : null;

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseFlowService.create(
                userRoles,
                requestService::hasRequestAssignedToProcess,
                processService::isProcessFinished,
                flow,
                response,
                destinationFlow
            ));
    }

    @PutMapping("/{idFlow}/{idResponse}")
    public ResponseEntity<Void> update (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idFlow") @NonNull final Integer idFlow,
                                        @PathVariable("idResponse") @NonNull final Integer idResponse,
                                        @RequestBody @Valid @NonNull final UpdateResponseFlowDTO updateResponseFlowDTO) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Flow destinationFlow = Objects.nonNull(updateResponseFlowDTO.destinationFlowId()) ? flowService.getById(updateResponseFlowDTO.destinationFlowId()) : null;

        responseFlowService.update(
            userRoles,
            idFlow, idResponse,
            requestService::hasRequestAssignedToProcess,
            processService::isProcessFinished,
            destinationFlow
        );

        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{idFlow}/{idResponse}")
    public ResponseEntity<Void> delete (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                        @PathVariable("idFlow") @NonNull final Integer idFlow,
                                        @PathVariable("idResponse") @NonNull final Integer idResponse) {
        final String userEmail = jwtService.extractUserEmail(Utils.removeBearerPrefix(token));
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);

        responseFlowService.delete(
            userRoles,
            idFlow,
            idResponse,
            requestService::hasRequestAssignedToProcess,
            processService::isProcessFinished
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}