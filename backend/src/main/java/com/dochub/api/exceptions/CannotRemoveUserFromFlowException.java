package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotRemoveUserFromFlowException extends RuntimeException {
    public CannotRemoveUserFromFlowException() {
        super(Constants.CANNOT_REMOVE_USER_FROM_FLOW_EXCEPTION);
    }
}
