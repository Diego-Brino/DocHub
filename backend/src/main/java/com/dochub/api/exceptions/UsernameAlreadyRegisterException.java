package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class UsernameAlreadyRegisterException extends RuntimeException {
    public UsernameAlreadyRegisterException () {
        super(Constants.USERNAME_ALREADY_REGISTER_EXCEPTION_MESSAGE);
    }
}
