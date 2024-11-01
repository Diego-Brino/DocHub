package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InputStreamReadException extends RuntimeException {
    public InputStreamReadException () {
        super(Constants.INPUT_STREM_READ_EXCEPTION_MESSAGE);
    }
}