package com.dochub.api.exceptions.s3;

import com.dochub.api.utils.Constants;

public class PresignedUrlGenerationException extends RuntimeException {
    public PresignedUrlGenerationException() {
        super(Constants.PRESIGNED_URL_GENERATION_EXCEPTION_MESSAGE);
    }
}