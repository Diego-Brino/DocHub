package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotDeleteAdminRoleException extends RuntimeException {
    public CannotDeleteAdminRoleException() {
        super(Constants.CANNOT_DELETE_ADMIN_ROLE_EXCEPTION);
    }
}