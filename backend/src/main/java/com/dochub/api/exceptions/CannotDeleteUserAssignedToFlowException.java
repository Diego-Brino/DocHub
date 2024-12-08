package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteUserAssignedToFlowException extends RuntimeException {
    public CannotDeleteUserAssignedToFlowException () {
        super(Constants.CANNOT_DELETE_USER_ASSIGNED_TO_FLOW_EXCEPTION_MESSAGE);
    }
}