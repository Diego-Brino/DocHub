package com.dochub.api.controllers;

import com.dochub.api.dtos.auth.AuthenticationRequestDTO;
import com.dochub.api.dtos.auth.AuthenticationResponseDTO;
import com.dochub.api.dtos.user.CreateUserDTO;
import com.dochub.api.entity.User;
import com.dochub.api.services.AuthenticationService;
import com.dochub.api.services.JwtService;
import com.dochub.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register (@ModelAttribute @Valid final CreateUserDTO createUserDTO) {
        final User user = userService.create(createUserDTO);
        final String token = jwtService.generateToken(user);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authenticationService.buildAuthenticationResponse(user, token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate (@RequestBody @Valid final AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity
            .ok()
            .body(authenticationService.authenticate(authenticationRequestDTO, userService::getByEmail, jwtService::generateToken));
    }
}