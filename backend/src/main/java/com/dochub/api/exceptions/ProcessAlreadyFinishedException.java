package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ProcessAlreadyFinishedException extends RuntimeException {
    public ProcessAlreadyFinishedException() {
        super(Constants.PROCESS_ALREADY_FINISHED_EXCEPTION_MESSAGE);
    }
}
