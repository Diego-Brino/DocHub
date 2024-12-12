package com.dochub.api.controllers;

import com.dochub.api.dtos.flow.FlowResponseDTO;
import com.dochub.api.dtos.request.CreateRequestDTO;
import com.dochub.api.dtos.request.RequestResponseDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.Process;
import com.dochub.api.entities.User;
import com.dochub.api.services.*;
import com.dochub.api.utils.Constants;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ProcessService processService;
    private final RequestService requestService;
    private final FlowService flowService;

    @GetMapping
    public ResponseEntity<List<RequestResponseDTO>> getAll () {
        return ResponseEntity
            .ok()
            .body(requestService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestResponseDTO> getOne (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(requestService.getDtoById(id));
    }

    @GetMapping("/{id}/actual-flow")
    public ResponseEntity<FlowResponseDTO> getActualFlow (@PathVariable("id") @NonNull final Integer id) {
        return ResponseEntity
            .ok()
            .body(flowService.getActualRequestFlow(id));
    }

    @PostMapping
    public ResponseEntity<Integer> create (@RequestHeader(Constants.AUTHORIZATION_HEADER) final String token,
                                           @RequestBody @Valid @NonNull final CreateRequestDTO createRequestDTO) {
        final String userEmail = jwtService.extractUserEmail(token);
        final User user = userService.getByEmail(userEmail);
        final UserRoleResponseDTO userRoles = userRoleService.getUserRolesByUser(user);
        final Process process = processService.getById(createRequestDTO.processId());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(requestService.create(
                userRoles,
                processService::isProcessFinished,
                user,
                process
            ));
    }
}