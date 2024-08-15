package com.dochub.api.controllers;

import com.dochub.api.dtos.AuthenticationRequestDTO;
import com.dochub.api.dtos.AuthenticationResponseDTO;
import com.dochub.api.dtos.RegisterUserDTO;
import com.dochub.api.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register (@RequestBody @Valid final RegisterUserDTO registerUserDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authenticationService.register(registerUserDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate (@RequestBody @Valid final AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity
            .ok()
            .body(authenticationService.authenticate(authenticationRequestDTO));
    }
}