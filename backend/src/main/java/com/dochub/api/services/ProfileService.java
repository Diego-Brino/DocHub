package com.dochub.api.services;

import com.dochub.api.dtos.user.ProfileCreateUserDTO;
import com.dochub.api.dtos.user.ProfileUpdateUserPasswordDTO;
import com.dochub.api.dtos.user.UpdateUserDTO;
import com.dochub.api.dtos.user_roles.UserRoleResponseDTO;
import com.dochub.api.entities.User;
import com.dochub.api.repositories.UserRepository;
import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public Integer create (final UserRoleResponseDTO userRoles,
                           final ProfileCreateUserDTO profileCreateUserDTO,
                           final BiFunction<ProfileCreateUserDTO, String, Integer> createUserFunc) {
        Utils.checkPermission(userRoles, Constants.CREATE_USER_PERMISSION);

        return createUserFunc.apply(profileCreateUserDTO, userRoles.user().username());
    }

    public void update (final UserRoleResponseDTO editorUserRoles,
                        final BiConsumer<User, UpdateUserDTO> validateUserUpdateFunc,
                        final User targetUser, final UpdateUserDTO updateUserDTO) {
        Utils.checkPermission(editorUserRoles, Constants.UPDATE_USER_PROFILE_PERMISSION);

        validateUserUpdateFunc.accept(targetUser, updateUserDTO);

        Utils.updateFieldIfPresent(updateUserDTO.name(), targetUser::setName);
        Utils.updateFieldIfPresent(updateUserDTO.password(), password -> targetUser.setPassword(Utils.encodePassword(password)));
        Utils.updateFieldIfPresent(updateUserDTO.email(), targetUser::setEmail);
        Utils.updateFieldIfPresent(updateUserDTO.username(), targetUser::setUsername);
        _updateAvatarIfPresent(updateUserDTO.avatar(), targetUser);

        _logAuditForChange(targetUser, editorUserRoles.user().username());

        userRepository.save(targetUser);
    }

    public void updateAvatar (final UserRoleResponseDTO editorUserRoles, final User targetUser, final MultipartFile avatar) {
        Utils.checkPermission(editorUserRoles, Constants.UPDATE_USER_PROFILE_PERMISSION);

        targetUser.setAvatar(Utils.readBytesFromMultipartFile(avatar));

        _logAuditForChange(targetUser, editorUserRoles.user().username());

        userRepository.save(targetUser);
    }

    public void updatePassword (final UserRoleResponseDTO editorUserRoles, final User targetUser, final ProfileUpdateUserPasswordDTO profileUpdateUserPasswordDTO) {
        Utils.checkPermission(editorUserRoles, Constants.UPDATE_USER_PROFILE_PERMISSION);

        targetUser.setPassword(Utils.encodePassword(profileUpdateUserPasswordDTO.newPassword()));

        _logAuditForChange(targetUser, editorUserRoles.user().username());

        userRepository.save(targetUser);
    }

    private void _updateAvatarIfPresent (final MultipartFile avatar, final User user) {
        if (Objects.nonNull(avatar)) {
            user.setAvatar(Utils.readBytesFromMultipartFile(avatar));
        }
    }

    private void _logAuditForChange (final User user, final String actor) {
        user.getAuditRecord().setAlterationUser(actor);
        user.getAuditRecord().setAlterationDate(new Date());
    }
}