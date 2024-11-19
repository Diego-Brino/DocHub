package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteServiceException extends RuntimeException {
    public CannotDeleteServiceException () {
        super(Constants.CANNOT_DELETE_SERVICE_EXCEPTION_MESSAGE);
    }
}