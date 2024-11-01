package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class DeleteBucketException extends RuntimeException {
    public DeleteBucketException (final String bucketName) {
        super(String.format(Constants.DELETE_BUCKET_EXCEPTION_MESSAGE, bucketName));
    }
}