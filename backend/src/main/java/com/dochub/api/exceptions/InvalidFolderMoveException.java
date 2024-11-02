package com.dochub.api.exceptions;

import com.dochub.api.utils.Constants;

public class InvalidFolderMoveException extends RuntimeException {
    public InvalidFolderMoveException () {
        super(Constants.INVALID_FOLDER_MOVE_EXCEPTION_MESSAGE);
    }
}