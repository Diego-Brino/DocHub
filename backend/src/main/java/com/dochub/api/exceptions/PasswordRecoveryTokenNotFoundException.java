package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class PasswordRecoveryTokenNotFoundException extends RuntimeException {
    public PasswordRecoveryTokenNotFoundException() {
        super(Constants.PASSWORD_RECOVERY_TOKEN_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}