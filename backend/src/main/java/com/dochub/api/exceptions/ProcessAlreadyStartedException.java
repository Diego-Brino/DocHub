package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ProcessAlreadyStartedException extends RuntimeException {
    public ProcessAlreadyStartedException() {
        super(Constants.PROCESS_ALREADY_STARTED_EXCEPTION_MESSAGE);
    }
}
