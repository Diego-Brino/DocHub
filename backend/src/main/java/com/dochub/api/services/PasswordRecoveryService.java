package com.dochub.api.services;

import com.dochub.api.dtos.recover.RecoveryPasswordDTO;
import com.dochub.api.entity.PasswordRecoveryAudit;
import com.dochub.api.entity.User;
import com.dochub.api.enums.TokenStatus;
import com.dochub.api.exceptions.InvalidPasswordRecoveryTokenException;
import com.dochub.api.exceptions.PasswordRecoveryTokenNotFoundException;
import com.dochub.api.exceptions.UsedPasswordRecoveryTokenException;
import com.dochub.api.repositories.PasswordRecoveryAuditRepository;
import com.dochub.api.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {
    private final UserService userService;
    private final EmailService emailService;

    private final PasswordRecoveryAuditRepository passwordRecoveryAuditRepository;

    public void sendRecoveryMail (final String userEmail) {
        final User user = userService.getByEmail(userEmail);

        _invalidatePreviousPasswordRecoveryAudits(user);

        final String token  = _createPasswordRecoveryAudit(user);
        final String recoveryLink = _getLinkToRecoverPassword(token);

        emailService.sendPasswordRecoveryMail(userEmail, recoveryLink);
    }

    public void changePassword (final RecoveryPasswordDTO recoveryPasswordDTO) {
        final PasswordRecoveryAudit passwordRecoveryAudit = passwordRecoveryAuditRepository
            .findByToken(recoveryPasswordDTO.token())
            .orElseThrow(PasswordRecoveryTokenNotFoundException::new);

        _validateTokenStatus(passwordRecoveryAudit.getStatus());
        _markPasswordRecoveryAuditsAsUsed(passwordRecoveryAudit);

        userService.updatePasswordByRecoveryLink(passwordRecoveryAudit.getUser(), recoveryPasswordDTO.newPassword());
    }

    private void _invalidatePreviousPasswordRecoveryAudits (final User user) {
        final Optional<List<PasswordRecoveryAudit>> passwordRecoveryAudits = passwordRecoveryAuditRepository.findAllByUser(user);

        if (passwordRecoveryAudits.isPresent()) {
            passwordRecoveryAudits.get().forEach(p -> p.setStatus(TokenStatus.INVALID));

            passwordRecoveryAuditRepository.saveAll(passwordRecoveryAudits.get());
        }
    }

    private String _createPasswordRecoveryAudit (final User user) {
        final PasswordRecoveryAudit passwordRecoveryAudit = new PasswordRecoveryAudit(user);

        return passwordRecoveryAuditRepository.save(passwordRecoveryAudit).getToken();
    }

    private String _getLinkToRecoverPassword (final String token) {
        return String.format(Constants.PASSWORD_RESET_LINK, token);
    }

    private void _validateTokenStatus (final TokenStatus tokenStatus) {
        switch (tokenStatus) {
            case INVALID -> throw new InvalidPasswordRecoveryTokenException();
            case USED -> throw new UsedPasswordRecoveryTokenException();
        }
    }

    private void _markPasswordRecoveryAuditsAsUsed (final PasswordRecoveryAudit passwordRecoveryAudit) {
        passwordRecoveryAudit.setStatus(TokenStatus.USED);

        passwordRecoveryAuditRepository.save(passwordRecoveryAudit);
    }
}