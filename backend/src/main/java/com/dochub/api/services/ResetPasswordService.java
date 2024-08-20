package com.dochub.api.services;

import com.dochub.api.dtos.ChangeUserPasswordByResetLinkDTO;
import com.dochub.api.entity.PasswordResetAudit;
import com.dochub.api.entity.User;
import com.dochub.api.enums.TokenStatus;
import com.dochub.api.exceptions.PasswordResetAuditTokenInvalidException;
import com.dochub.api.exceptions.PasswordResetAuditTokenNotFoundException;
import com.dochub.api.exceptions.PasswordResetAuditTokenUsedException;
import com.dochub.api.repositories.PasswordResetAuditRepository;
import com.dochub.api.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {
    private final UserService userService;
    private final EmailService emailService;

    private final PasswordResetAuditRepository passwordResetAuditRepository;

    public void sendMailToResetPassword (final String userEmail) {
        final User user = userService.getByEmail(userEmail);

        _invalidatePreviousPasswordResetAudits(user);

        final String token  = _createPasswordResetAudit(user);
        final String resetLink = _getLinkToResetPassword(token);

        emailService.sendPasswordResetEmail(userEmail, resetLink);
    }

    public void changePassword (final ChangeUserPasswordByResetLinkDTO changeUserPasswordByResetLinkDTO) {
        final PasswordResetAudit passwordResetAudit = passwordResetAuditRepository
            .findByToken(changeUserPasswordByResetLinkDTO.token())
            .orElseThrow(PasswordResetAuditTokenNotFoundException::new);

        _validarTokenStatus(passwordResetAudit.getStatus());
        _markPasswordResetAuditsAsUsed(passwordResetAudit);

        userService.updatePasswordByResetLink(passwordResetAudit.getUser(), changeUserPasswordByResetLinkDTO.newPassword());
    }

    private void _invalidatePreviousPasswordResetAudits (final User user) {
        final Optional<List<PasswordResetAudit>> passwordResetAudits = passwordResetAuditRepository.findAllByUser(user);

        if (passwordResetAudits.isPresent()) {
            passwordResetAudits.get().forEach(p -> p.setStatus(TokenStatus.INVALID));

            passwordResetAuditRepository.saveAll(passwordResetAudits.get());
        }
    }

    private String _createPasswordResetAudit (final User user) {
        final PasswordResetAudit passwordResetAudit = new PasswordResetAudit(user);

        return passwordResetAuditRepository.save(passwordResetAudit).getToken();
    }

    private String _getLinkToResetPassword (final String token) {
        return String.format(Constants.PASSWORD_RESET_LINK, token);
    }

    private void _validarTokenStatus (final TokenStatus tokenStatus) {
        switch (tokenStatus) {
            case INVALID -> throw new PasswordResetAuditTokenInvalidException();
            case USED -> throw new PasswordResetAuditTokenUsedException();
        }
    }

    private void _markPasswordResetAuditsAsUsed (final PasswordResetAudit passwordResetAudit) {
        passwordResetAudit.setStatus(TokenStatus.USED);

        passwordResetAuditRepository.save(passwordResetAudit);
    }
}