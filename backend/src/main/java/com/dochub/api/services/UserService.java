package com.dochub.api.services;

import com.dochub.api.dtos.UpdateUserDTO;
import com.dochub.api.dtos.UserResponseDTO;
import com.dochub.api.entity.User;
import com.dochub.api.exceptions.EntityNotFoundByEmailException;
import com.dochub.api.exceptions.EntityNotFoundByIdException;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserResponseDTO getByToken (final String token) {
        final User user = _getUserByToken(token);

        return new UserResponseDTO(user);
    }

    public User getByEmail (final String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(EntityNotFoundByEmailException::new);
    }

    public List<UserResponseDTO> getAll () {
        final List<User> users = userRepository.findAll();

        return users
            .stream()
            .map(UserResponseDTO::new)
            .collect(Collectors.toList());
    }

    public byte[] getAvatar (final Integer id) {
        final User user = _getUserById(id);

        if (Objects.nonNull(user.getAvatar())) {
            return user.getAvatar();
        }

        return _getDefaultUserAvatar();
    }

    public void update (final String token, final UpdateUserDTO updateUserDTO) {
        final User user = _getUserByToken(token);

        if (Objects.nonNull(updateUserDTO.name()) && !updateUserDTO.name().isBlank()) {
            user.setName(updateUserDTO.name());
        }

        if (Objects.nonNull(updateUserDTO.password()) && !updateUserDTO.name().isBlank()) {
            user.setPassword(Utils.encodePassword(updateUserDTO.password()));
        }

        if (Objects.nonNull(updateUserDTO.email()) && !updateUserDTO.email().isBlank()) {
            user.setEmail(updateUserDTO.email());
        }

        if (Objects.nonNull(updateUserDTO.username()) && !updateUserDTO.username().isBlank()) {
            user.setUsername(updateUserDTO.username());
        }

        if (Objects.nonNull(updateUserDTO.avatar())) {
            user.setAvatar(Utils.readBytesFromMultipartFile(updateUserDTO.avatar()));
        }

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    public void updateAvatar (final String token, final MultipartFile avatar) {
        final User user = _getUserByToken(token);

        user.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    public void updatePassword (final String token, final String newPassword) {
        final User user = _getUserByToken(token);

        user.setPassword(Utils.encodePassword(newPassword));

        _fillAuditRecord(user);

        userRepository.save(user);
    }

    public void updatePasswordByResetLink (final User user, final String newPassword) {
        user.setPassword(Utils.encodePassword(newPassword));

        _fillAuditRecordByResetLink(user);

        userRepository.save(user);
    }

    private User _getUserById (final Integer id) {
        return userRepository
            .findById(id)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    private User _getUserByToken (final String token) {
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

    private void _fillAuditRecordByResetLink (final User user) {
        user.getAuditRecord().setAlterationUser(Constants.PASSWORD_RESET_INITIATOR_NAME);
        user.getAuditRecord().setAlterationDate(new Date());
    }
}