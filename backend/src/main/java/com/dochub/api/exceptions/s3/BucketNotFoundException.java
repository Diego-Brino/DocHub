package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException (final String bucketName) {
        super(String.format(Constants.BUCKET_NOT_FOUND_EXCEPTION, bucketName));
    }
}