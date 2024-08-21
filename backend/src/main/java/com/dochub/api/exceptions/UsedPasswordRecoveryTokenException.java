package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class UsedPasswordRecoveryTokenException extends RuntimeException {
    public UsedPasswordRecoveryTokenException() {
        super(Constants.USED_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE);
    }
}