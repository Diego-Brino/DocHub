package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class EmailAlreadyRegisterException extends RuntimeException {
    public EmailAlreadyRegisterException () {
        super(Constants.EMAIL_ALREADY_REGISTER_EXCEPTION_MESSAGE);
    }
}