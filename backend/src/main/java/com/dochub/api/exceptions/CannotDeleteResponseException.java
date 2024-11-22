package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteResponseException extends RuntimeException {
    public CannotDeleteResponseException () {
        super(Constants.CANNOT_DELETE_RESPONSE_EXCEPTION_MESSAGE);
    }
}
