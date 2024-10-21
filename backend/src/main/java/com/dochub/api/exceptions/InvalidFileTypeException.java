package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException () {
        super(Constants.INVALID_FILE_TYPE_EXCEPTION);
    }
}