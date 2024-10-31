package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class CreateObjectException extends RuntimeException {
    public CreateObjectException () {
        super(Constants.CREATE_OBJECT_EXCEPTION_MESSAGE);
    }
}