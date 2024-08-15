package com.dochub.api.services;

import com.dochub.api.dtos.AuthenticationRequestDTO;
import com.dochub.api.dtos.AuthenticationResponseDTO;
import com.dochub.api.dtos.RegisterUserDTO;
import com.dochub.api.entity.User;
import com.dochub.api.exceptions.EmailAlreadyRegisterException;
import com.dochub.api.exceptions.EntityNotFoundByEmailException;
import com.dochub.api.infra.security.JwtService;
import com.dochub.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register (final RegisterUserDTO registerUserDTO) {
        if(_isEmailAlreadyRegister(registerUserDTO.email())) {
            throw new EmailAlreadyRegisterException();
        }

        final User user = new User(registerUserDTO);

        userRepository.save(user);

        final String token = jwtService.generateToken(user);

        return new AuthenticationResponseDTO(token);
    }

    public AuthenticationResponseDTO authenticate (final AuthenticationRequestDTO authenticationRequestDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.email(),
                authenticationRequestDTO.password()
            )
        );

        final User user = userRepository
            .findByEmail(authenticationRequestDTO.email())
            .orElseThrow(EntityNotFoundByEmailException::new);

        final String token = jwtService.generateToken(user);

        return new AuthenticationResponseDTO(token);
    }

    private Boolean _isEmailAlreadyRegister (final String email) {
        final Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }
}