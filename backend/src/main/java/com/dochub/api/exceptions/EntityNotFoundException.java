package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException () {
        super(Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}