package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InputStreamFileReadException extends RuntimeException {
    public InputStreamFileReadException() {
        super(Constants.INPUT_STREAM_READ_EXCEPTION_MESSAGE);
    }
}