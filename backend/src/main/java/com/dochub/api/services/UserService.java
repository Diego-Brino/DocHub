package com.dochub.api.services;

import com.dochub.api.dtos.UpdateUserDTO;
import com.dochub.api.entity.User;
import com.dochub.api.exceptions.EntityNotFoundByEmailException;
import com.dochub.api.infra.security.JwtService;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public byte[] getUserAvatar (final String token) {
        final User user = _getUser(token);

        if (Objects.nonNull(user.getAvatar())) {
            return user.getAvatar();
        }

        return _getDefaultUserAvatar();
    }

    public void updateUser (final String token, final UpdateUserDTO updateUserDTO) {
        final User user = _getUser(token);

        if (Objects.nonNull(updateUserDTO.name()) && !updateUserDTO.name().isBlank()) user.setName(updateUserDTO.name());
        if (Objects.nonNull(updateUserDTO.password()) && !updateUserDTO.name().isBlank()) user.setPassword(Utils.encodePassword(updateUserDTO.password()));
        if (Objects.nonNull(updateUserDTO.email()) && !updateUserDTO.email().isBlank()) user.setEmail(updateUserDTO.email());
        if (Objects.nonNull(updateUserDTO.username()) && !updateUserDTO.username().isBlank()) user.setUsername(updateUserDTO.username());
        if (Objects.nonNull(updateUserDTO.avatar())) user.setAvatar(Utils.readBytesFromMultipartFile(updateUserDTO.avatar()));

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    public void updateUserAvatar (final String token, final MultipartFile avatar) {
        final User user = _getUser(token);

        user.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    public void updateUserPassword (final String token, final String password) {
        final User user = _getUser(token);

        user.setPassword(Utils.encodePassword(password));

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    private User _getUser (final String token) {
        final String formattedToken = Utils.removeBearerPrefix(token);

        final String userEmail = jwtService.extractUserEmail(formattedToken);

        return userRepository
            .findByEmail(userEmail)
            .orElseThrow(EntityNotFoundByEmailException::new);
    }

    private byte[] _getDefaultUserAvatar () {
        final ClassPathResource classPathResource = new ClassPathResource(Constants.USER_ICON_PATH);

        return Utils.readBytesFromResource(classPathResource);
    }

    private void _fillAuditRecord (final User user) {
        user.getAuditRecord().setAlterationUser(user.getUsername());
        user.getAuditRecord().setAlterationDate(new Date());
    }
}