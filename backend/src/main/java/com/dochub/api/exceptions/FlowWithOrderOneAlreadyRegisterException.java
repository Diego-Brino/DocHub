package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class FlowWithOrderOneAlreadyRegisterException extends RuntimeException {
    public FlowWithOrderOneAlreadyRegisterException () {
        super(Constants.FLOW_WITH_ORDER_ONE_ALREADY_REGISTER_EXCEPTION_MESSAGE);
    }
}