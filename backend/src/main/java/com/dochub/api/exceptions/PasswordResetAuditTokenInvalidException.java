package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class PasswordResetAuditTokenInvalidException extends RuntimeException {
    public PasswordResetAuditTokenInvalidException() {
        super(Constants.INVALIDATED_PASSWORD_RESET_TOKEN_EXCEPTION_MESSAGE);
    }
}