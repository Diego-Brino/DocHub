package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class MultipartFileReadException extends RuntimeException {
    public MultipartFileReadException() {
        super(Constants.MULTIPART_FILE_READ_EXCEPTION_MESSAGE);
    }
}
