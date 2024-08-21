package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException() {
        super(Constants.USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE);
    }
}