package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class EntityNotFoundByEmailException extends RuntimeException {
    public EntityNotFoundByEmailException () {
        super(Constants.ENTITY_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE);
    }
}