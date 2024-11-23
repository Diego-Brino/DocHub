package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ResponseInUseException extends RuntimeException {
    public ResponseInUseException () {
        super(Constants.RESPONSE_IN_USE_EXCEPTION_MESSAGE);
    }
}
