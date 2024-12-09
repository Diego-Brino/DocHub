package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class RequestAlreadyFinishedException extends RuntimeException {
    public RequestAlreadyFinishedException () {
        super(Constants.REQUEST_ALREADY_FINISHED_EXCEPTION_MESSAGE);
    }
}