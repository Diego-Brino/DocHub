package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ServiceHasProcessesInProgressException extends RuntimeException {
    public ServiceHasProcessesInProgressException () {
        super(Constants.SERVICE_HAS_PROCESSES_IN_PROGRESS_EXCEPTION_MESSAGE);
    }
}
