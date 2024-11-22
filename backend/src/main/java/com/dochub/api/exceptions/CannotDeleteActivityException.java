package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteActivityException extends RuntimeException {
    public CannotDeleteActivityException () {
        super(Constants.CANNOT_DELETE_ACTIVITY_EXCEPTION_MESSAGE);
    }
}