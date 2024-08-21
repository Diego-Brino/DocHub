package com.dochub.api.services;

import com.dochub.api.dtos.auth.AuthenticationRequestDTO;
import com.dochub.api.dtos.auth.AuthenticationResponseDTO;
import com.dochub.api.dtos.user.CreateUserDTO;
import com.dochub.api.entity.User;
import com.dochub.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthenticationResponseDTO register (final CreateUserDTO createUserDTO) {
        final String token = userService.create(createUserDTO);

        return new AuthenticationResponseDTO(token);
    }

    public AuthenticationResponseDTO authenticate (final AuthenticationRequestDTO authenticationRequestDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.email(),
                authenticationRequestDTO.password()
            )
        );

        final User user = userService.getByEmail(authenticationRequestDTO.email());
        final String token = jwtService.generateToken(user);

        return new AuthenticationResponseDTO(token);
    }
}