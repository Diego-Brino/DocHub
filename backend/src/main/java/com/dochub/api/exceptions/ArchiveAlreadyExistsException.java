package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ArchiveAlreadyExistsException extends RuntimeException {
    public ArchiveAlreadyExistsException () {
        super(Constants.ARCHIVE_ALREADY_EXISTS_EXCEPTION_MESSAGE);
    }
}