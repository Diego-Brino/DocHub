package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ServiceHasProcessAssignedException extends RuntimeException {
    public ServiceHasProcessAssignedException () {
        super(Constants.SERVICE_HAS_PROCESS_ASSIGNED_EXCEPTION_MESSAGE);
    }
}