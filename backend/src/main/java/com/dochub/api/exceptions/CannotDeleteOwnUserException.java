package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteOwnUserException extends RuntimeException {
    public CannotDeleteOwnUserException () {
        super(Constants.CANNOT_DELETE_OWN_USER_EXCEPTION);
    }
}
