package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InvalidPasswordRecoveryTokenException extends RuntimeException {
    public InvalidPasswordRecoveryTokenException() {
        super(Constants.INVALID_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE);
    }
}