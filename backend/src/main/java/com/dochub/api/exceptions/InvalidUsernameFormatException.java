package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InvalidUsernameFormatException extends RuntimeException {
    public InvalidUsernameFormatException() {
        super(Constants.INVALID_USERNAME_FORMAT_EXCEPTION_MESSAGE);
    }
}
