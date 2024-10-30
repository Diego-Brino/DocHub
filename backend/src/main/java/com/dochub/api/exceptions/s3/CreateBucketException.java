package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class CreateBucketException extends RuntimeException {
    public CreateBucketException () {
        super(Constants.CREATE_BUCKET_EXCEPTION_MESSAGE);
    }
}