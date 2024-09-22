package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class GroupCannontBeDeletedException extends RuntimeException {
    public GroupCannontBeDeletedException () {
        super(Constants.GROUP_CANNOT_BE_DELETED_EXCEPTION_MESSAGE);
    }
}
