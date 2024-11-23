package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class ActivityInUseException extends RuntimeException {
    public ActivityInUseException() {
        super(Constants.ACTIVITY_IN_USE_EXCEPTION_MESSAGE);
    }
}
