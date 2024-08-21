package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException () {
        super(Constants.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);
    }
}