package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class CannotEditAdminRoleException extends RuntimeException {
    public CannotEditAdminRoleException () {
        super(Constants.CANNOT_EDIT_ADMIN_ROLE_EXCEPTION);
    }
}