package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class PasswordResetAuditTokenUsedException extends RuntimeException {
    public PasswordResetAuditTokenUsedException () {
        super(Constants.USED_PASSWORD_RESET_TOKEN_EXCEPTION_MESSAGE);
    }
}