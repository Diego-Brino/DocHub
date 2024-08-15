package com.dochub.api.handler;

import com.dochub.api.dtos.ErrorDTO;
import com.dochub.api.exceptions.EmailAlreadyRegisterException;
import com.dochub.api.exceptions.EntityNotFoundByEmailException;
import com.dochub.api.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApplicationHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericError (Exception e) {
        log.error(Constants.GENERIC_ERROR_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(Constants.GENERIC_ERROR_EXCEPTION_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions (MethodArgumentNotValidException e) {
        final Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StringBuilder errorMessageBuilder = new StringBuilder();
        errors.forEach((field, message) -> errorMessageBuilder.append(message).append("; "));

        log.error(errorMessageBuilder.toString(), e);

        final ErrorDTO errorDTO = new ErrorDTO(errorMessageBuilder.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleBadCredentialsException (BadCredentialsException e) {
        log.error(Constants.BAD_CREDENTIALS_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(Constants.BAD_CREDENTIALS_EXCEPTION_MESSAGE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(EmailAlreadyRegisterException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyRegisterException (EmailAlreadyRegisterException e) {
        log.error(Constants.EMAIL_ALREADY_REGISTER_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundByEmailException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundByEmailException (EntityNotFoundByEmailException e) {
        log.error(Constants.ENTITY_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}