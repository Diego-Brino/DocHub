package com.dochub.api.services;

import com.dochub.api.dtos.user.*;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.exceptions.*;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final EmailService emailService;

    private final UserRepository userRepository;

    public User getById (final Integer userId) {
        return userRepository
            .findById(userId)
            .orElseThrow(EntityNotFoundByIdException::new);
    }

    public UserResponseDTO getDtoById (final Integer userId) {
        final User user = getById(userId);

        return new UserResponseDTO(user);
    }

    public UserResponseDTO getDtoById (final Integer userId, final String userEmail) {
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

        return Utils.getImageFromClasspath(Constants.USER_ICON_PATH);
    }

    public User create (final CreateUserDTO createUserDTO) {
        _validateUserCreation(createUserDTO);

        final User user = new User(createUserDTO);

        return userRepository.save(user);
    }

    public Integer create (final ProfileCreateUserDTO profileCreateUserDTO, final String initiatorUsername) {
        _validateUserCreation(profileCreateUserDTO);

        final String randomPassword = Utils.generateRandomPassword();
        final User user = new User(profileCreateUserDTO, randomPassword, initiatorUsername);

        final Integer userId = userRepository.save(user).getId();

        emailService.sendAccountCreationMail(user.getName(), user.getEmail(), randomPassword);

        return userId;
    }

    public User update (final Integer userId, final String userEmail, final UpdateUserDTO updateUserDTO) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        validateUserUpdate(user, updateUserDTO);

        Utils.updateFieldIfPresent(updateUserDTO.name(), user::setName);
        Utils.updateFieldIfPresent(updateUserDTO.password(), password -> user.setPassword(Utils.encodePassword(password)));
        Utils.updateFieldIfPresent(updateUserDTO.email(), user::setEmail);
        Utils.updateFieldIfPresent(updateUserDTO.username(), user::setUsername);
        _updateAvatarIfPresent(updateUserDTO.avatar(), user);

        _logAuditForChange(user, user.getRealUsername());

        return userRepository.save(user);
    }

    public void updateAvatar (final Integer userId, final String userEmail, final MultipartFile avatar) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        Utils.validateImageType(avatar);

        user.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _logAuditForChange(user, user.getRealUsername());

        userRepository.save(user);
    }

    public User updatePassword (final Integer userId, final String userEmail, final UpdatePasswordDTO updatePasswordDTO) {
        final User user = getByEmail(userEmail);

        _validateUserIdentity(userId, user);

        _validatePasswordMatching(updatePasswordDTO.oldPassword(), user.getPassword());

        user.setPassword(Utils.encodePassword(updatePasswordDTO.newPassword()));

        _logAuditForChange(user, user.getRealUsername());

        return userRepository.save(user);
    }

    public void updatePasswordByRecoveryLink (final User user, final String newPassword) {
        user.setPassword(Utils.encodePassword(newPassword));

        _logAuditForPasswordRecovery(user);

        userRepository.save(user);
    }

    public UpdateUserResponseDTO generateNewToken (final User user, final BiFunction<Map<String, Object>, User, String> generateToken) {
        final HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        return new UpdateUserResponseDTO(generateToken.apply(extraClaims, user));
    }

    public void validateUserUpdate (final User user, final UpdateUserDTO updateUserDTO) {
        if (Objects.nonNull(updateUserDTO.email()) &&
                (!Objects.equals(user.getEmail(), updateUserDTO.email()) && _isEmailAlreadyRegister(updateUserDTO.email()))) {
            throw new EmailAlreadyRegisterException();
        }

        if (Objects.nonNull(updateUserDTO.username()) &&
                (!Objects.equals(user.getRealUsername(), updateUserDTO.username()) && _isUsernameAlreadyRegister(updateUserDTO.username()))) {
            throw new UsernameAlreadyRegisterException();
        }

        if (Objects.nonNull(updateUserDTO.username()) && Utils.containsSpacesOrSpecialCharacters(updateUserDTO.username())) {
            throw new InvalidUsernameFormatException();
        }

        if (Objects.nonNull(updateUserDTO.avatar())) {
            Utils.validateImageType(updateUserDTO.avatar());
        }
    }

    public void delete (final UserRoleResponseDTO userRoles, final Integer userId) {
        final User user = getById(userId);

        if (userRoles.user().id().equals(userId)) {
            throw new CannotDeleteOwnUserException();
        }

        userRepository.delete(user);
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

        if (Objects.nonNull(createUserDTO.avatar())) {
            Utils.validateImageType(createUserDTO.avatar());
        }
    }

    private void _validateUserCreation (final ProfileCreateUserDTO profileCreateUserDTO) {
        if (_isEmailAlreadyRegister(profileCreateUserDTO.email())) {
            throw new EmailAlreadyRegisterException();
        }

        if (_isUsernameAlreadyRegister(profileCreateUserDTO.username())) {
            throw new UsernameAlreadyRegisterException();
        }

        if (Utils.containsSpacesOrSpecialCharacters(profileCreateUserDTO.username())) {
            throw new InvalidUsernameFormatException();
        }

        if (Objects.nonNull(profileCreateUserDTO.avatar())) {
            Utils.validateImageType(profileCreateUserDTO.avatar());
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

    private void _updateAvatarIfPresent (final MultipartFile avatar, final User user) {
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