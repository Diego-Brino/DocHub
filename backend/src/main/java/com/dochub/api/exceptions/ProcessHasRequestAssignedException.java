package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ProcessHasRequestAssignedException extends RuntimeException {
    public ProcessHasRequestAssignedException () {
        super(Constants.PROCESS_HAS_REQUEST_ASSIGNED_EXCEPTION_MESSAGE);
    }
}
