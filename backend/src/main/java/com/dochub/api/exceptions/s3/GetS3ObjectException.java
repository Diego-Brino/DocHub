package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class GetS3ObjectException extends RuntimeException {
    public GetS3ObjectException (final String bucketName, final String fileName) {
        super(String.format(Constants.GET_S3_OBJECT_EXCEPTION_MESSAGE, fileName, bucketName));
    }
}