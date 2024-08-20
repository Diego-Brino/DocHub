package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class PasswordResetAuditTokenNotFoundException extends RuntimeException {
    public PasswordResetAuditTokenNotFoundException () {
        super(Constants.PASSWORD_RESET_TOKEN_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}