package com.dochub.api.services;

import com.dochub.api.dtos.user.CreateUserDTO;
import com.dochub.api.dtos.user.UpdatePasswordDTO;
import com.dochub.api.dtos.user.UpdateUserDTO;
import com.dochub.api.dtos.user.UserResponseDTO;
import com.dochub.api.entity.User;
import com.dochub.api.exceptions.*;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO getById (final Integer userId, final String userEmail) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        return new UserResponseDTO(user);
    }

    public User getByEmail (final String userEmail) {
        return userRepository
            .findByEmail(userEmail)
            .orElseThrow(UserNotFoundByEmailException::new);
    }

    public List<UserResponseDTO> getAll () {
        final List<User> users = userRepository.findAll();

        return users
            .stream()
            .map(UserResponseDTO::new)
            .collect(Collectors.toList());
    }

    public byte[] getAvatar (final Integer userId) {
        final User user = userRepository
            .findById(userId)
            .orElseThrow(EntityNotFoundByIdException::new);

        if (Objects.nonNull(user.getAvatar())) {
            return user.getAvatar();
        }

        return _getDefaultUserAvatar();
    }

    public User create (final CreateUserDTO createUserDTO) {
        _validateUserCreation(createUserDTO);

        final User user = new User(createUserDTO);

        return userRepository.save(user);
    }

    public void update (final Integer userId, final String userEmail, final UpdateUserDTO updateUserDTO) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        _validateUserUpdate(user, updateUserDTO);

        Utils.updateFieldIfPresent(updateUserDTO.name(), user::setName);
        Utils.updateFieldIfPresent(updateUserDTO.password(), password -> user.setPassword(Utils.encodePassword(password)));
        Utils.updateFieldIfPresent(updateUserDTO.email(), user::setEmail);
        Utils.updateFieldIfPresent(updateUserDTO.username(), user::setUsername);
        _updateAvatarIfPresent(updateUserDTO.avatar(), user);

        _logAuditForChange(user, user.getUsername());

        userRepository.save(user);
    }

    public void updateAvatar (final Integer userId, final String userEmail, final MultipartFile avatar) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        user.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _logAuditForChange(user, user.getUsername());

        userRepository.save(user);
    }

    public void updatePassword (final Integer userId, final String userEmail, final UpdatePasswordDTO updatePasswordDTO) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        _validatePasswordMatching(updatePasswordDTO.oldPassword(), user.getPassword());

        user.setPassword(Utils.encodePassword(updatePasswordDTO.newPassword()));

        _logAuditForChange(user, user.getUsername());

        userRepository.save(user);
    }

    public void updatePasswordByRecoveryLink (final User user, final String newPassword) {
        user.setPassword(Utils.encodePassword(newPassword));

        _logAuditForPasswordRecovery(user);

        userRepository.save(user);
    }

    private byte[] _getDefaultUserAvatar () {
        final ClassPathResource classPathResource = new ClassPathResource(Constants.USER_ICON_PATH);

        return Utils.readBytesFromResource(classPathResource);
    }

    private void _validateUserIdentity (final Integer userId, final User user) {
        if (!Objects.equals(userId, user.getId())) {
            throw new PermissionDeniedException(Constants.USER_MISMATCH_EXCEPTION_MESSAGE);
        }
    }

    private void _validateUserCreation (final CreateUserDTO createUserDTO) {
        if (_isEmailAlreadyRegister(createUserDTO.email())) {
            throw new EmailAlreadyRegisterException();
        }

        if (_isUsernameAlreadyRegister(createUserDTO.username())) {
            throw new UsernameAlreadyRegisterException();
        }

        if (Utils.containsSpacesOrSpecialCharacters(createUserDTO.username())) {
            throw new InvalidUsernameFormatException();
        }
    }

    private void _validateUserUpdate (final User user, final UpdateUserDTO updateUserDTO) {
        if (Objects.nonNull(updateUserDTO.email()) &&
                (!Objects.equals(user.getEmail(), updateUserDTO.email()) && _isEmailAlreadyRegister(updateUserDTO.email()))) {
            throw new EmailAlreadyRegisterException();
        }

        if (Objects.nonNull(updateUserDTO.username()) &&
                (!Objects.equals(user.getUsername(), updateUserDTO.username()) && _isUsernameAlreadyRegister(updateUserDTO.username()))) {
            throw new UsernameAlreadyRegisterException();
        }

        if (Objects.nonNull(updateUserDTO.username()) && Utils.containsSpacesOrSpecialCharacters(updateUserDTO.username())) {
            throw new InvalidUsernameFormatException();
        }
    }

    private Boolean _isEmailAlreadyRegister (final String email) {
        final Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }

    private Boolean _isUsernameAlreadyRegister (final String username) {
        final Optional<User> user = userRepository.findByUsername(username);

        return user.isPresent();
    }

    private void _updateAvatarIfPresent (MultipartFile avatar, User user) {
        if (Objects.nonNull(avatar)) {
            user.setAvatar(Utils.readBytesFromMultipartFile(avatar));
        }
    }

    private void _validatePasswordMatching (final String oldPassword, final String encodedUserPassword) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(oldPassword, encodedUserPassword)) {
            throw new PasswordMismatchException();
        }
    }

    private void _logAuditForChange (final User user, final String actor) {
        user.getAuditRecord().setAlterationUser(actor);
        user.getAuditRecord().setAlterationDate(new Date());
    }

    private void _logAuditForPasswordRecovery (final User user) {
        user.getAuditRecord().setAlterationUser(Constants.PASSWORD_RESET_INITIATOR_NAME);
        user.getAuditRecord().setAlterationDate(new Date());
    }
}