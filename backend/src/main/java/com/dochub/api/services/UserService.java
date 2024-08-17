package com.dochub.api.services;

import com.dochub.api.entity.User;
import com.dochub.api.exceptions.EntityNotFoundByEmailException;
import com.dochub.api.infra.security.JwtService;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public byte[] getUserAvatar (final String token) {
        final String formattedToken = Utils.removeBearerPrefix(token);

        final String userEmail = jwtService.extractUserEmail(formattedToken);

        final User user = userRepository
            .findByEmail(userEmail)
            .orElseThrow(EntityNotFoundByEmailException::new);

        if (Objects.nonNull(user.getAvatar())) {
            return user.getAvatar();
        }

        return _getDefaultUserAvatar();
    }

    private byte[] _getDefaultUserAvatar () {
        final ClassPathResource classPathResource = new ClassPathResource(Constants.USER_ICON_PATH);

        return Utils.readBytesFromResource(classPathResource);
    }
}