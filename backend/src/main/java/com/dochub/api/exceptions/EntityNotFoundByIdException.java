package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class EntityNotFoundByIdException extends RuntimeException {
    public EntityNotFoundByIdException () {
        super(Constants.ENTITY_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE);
    }
}