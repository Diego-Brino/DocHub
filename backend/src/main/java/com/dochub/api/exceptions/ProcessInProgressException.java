package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ProcessInProgressException extends RuntimeException {
    public ProcessInProgressException () {
        super(Constants.PROCESS_IN_PROGRESS_EXCEPTION_MESSAGE);
    }
}
