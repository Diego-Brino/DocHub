package com.dochub.api.controllers;

import com.dochub.api.dtos.auth.AuthenticationRequestDTO;
import com.dochub.api.dtos.auth.AuthenticationResponseDTO;
import com.dochub.api.dtos.user.CreateUserDTO;
import com.dochub.api.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register (@ModelAttribute @Valid final CreateUserDTO createUserDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authenticationService.register(createUserDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate (@RequestBody @Valid final AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity
            .ok()
            .body(authenticationService.authenticate(authenticationRequestDTO));
    }
}