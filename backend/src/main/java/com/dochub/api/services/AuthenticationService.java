package com.dochub.api.services;

import com.dochub.api.dtos.auth.AuthenticationRequestDTO;
import com.dochub.api.dtos.auth.AuthenticationResponseDTO;
import com.dochub.api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO buildAuthenticationResponse (final User user, final String token) {
        return new AuthenticationResponseDTO(token);
    }

    public AuthenticationResponseDTO authenticate (final AuthenticationRequestDTO authenticationRequestDTO,
                                                   final Function<String, User> getUserById, final BiFunction<Map<String, Object>, User, String> generateToken) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.email(),
                authenticationRequestDTO.password()
            )
        );

        final User user = getUserById.apply(authenticationRequestDTO.email());

        final HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        final String token = generateToken.apply(extraClaims, user);

        return buildAuthenticationResponse(user, token);
    }
}