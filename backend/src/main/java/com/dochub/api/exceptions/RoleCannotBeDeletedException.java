package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class RoleCannotBeDeletedException extends RuntimeException {
    public RoleCannotBeDeletedException () {
        super(Constants.ROLE_CANNOT_BE_DELETED_EXCEPTION_MESSAGE);
    }
}
