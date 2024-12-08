package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class FlowInteractionNotAuthorizedException extends RuntimeException {
    public FlowInteractionNotAuthorizedException () {
        super(Constants.FLOW_INTERACTION_NOT_AUTHORIZED_EXCEPTION_MESSAGE);
    }
}