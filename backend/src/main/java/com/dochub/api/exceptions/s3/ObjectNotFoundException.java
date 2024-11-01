package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException (final String bucketName, final String fileName) {
        super(String.format(Constants.OBJECT_NOT_FOUND_EXCEPTION, fileName, bucketName));
    }
}