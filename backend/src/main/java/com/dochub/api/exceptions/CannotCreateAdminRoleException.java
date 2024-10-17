package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotCreateAdminRoleException extends RuntimeException {
    public CannotCreateAdminRoleException () {
        super(Constants.CANNOT_CREATE_ADMIN_ROLE_EXCEPTION);
    }
}