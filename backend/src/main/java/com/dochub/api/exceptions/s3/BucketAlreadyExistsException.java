package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class BucketAlreadyExistsException extends RuntimeException {
    public BucketAlreadyExistsException (final String bucketName) {
        super(String.format(Constants.BUCKET_ALREADY_EXISTS_EXCEPTION_MESSAGE, bucketName));
    }
}