package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InvalidTokenFormatException extends RuntimeException {
    public InvalidTokenFormatException () {
        super(Constants.INVALID_TOKEN_FORMAT_EXCEPTION_MESSAGE);
    }
}