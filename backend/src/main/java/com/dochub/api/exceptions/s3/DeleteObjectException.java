package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class DeleteObjectException extends RuntimeException {
    public DeleteObjectException (final String bucketName, final String fileName) {
        super(String.format(Constants.DELETE_OBJECT_EXCEPTION_MESSAGE, fileName, bucketName));
    }
}