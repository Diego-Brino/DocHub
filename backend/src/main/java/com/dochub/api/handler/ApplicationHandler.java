package com.dochub.api.handler;

import com.dochub.api.dtos.ErrorDTO;
import com.dochub.api.exceptions.*;
import com.dochub.api.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
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
        log.error(Constants.INVALID_CREDENTIALS_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(Constants.INVALID_CREDENTIALS_EXCEPTION_MESSAGE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDTO> handleExpiredJwtException (ExpiredJwtException e) {
        log.error(Constants.EXPIRED_TOKEN_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(Constants.EXPIRED_TOKEN_EXCEPTION_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(EmailAlreadyRegisterException.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyRegisterException (EmailAlreadyRegisterException e) {
        log.error(Constants.EMAIL_ALREADY_REGISTERED_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UsernameAlreadyRegisterException.class)
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyRegisterException (UsernameAlreadyRegisterException e) {
        log.error(Constants.USERNAME_ALREADY_REGISTERED_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundByEmailException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundByEmailException (EntityNotFoundByEmailException e) {
        log.error(Constants.ENTITY_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundByIdException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundByIdException (EntityNotFoundByIdException e) {
        log.error(Constants.ENTITY_NOT_FOUND_BY_ID_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MultipartFileReadException.class)
    public ResponseEntity<ErrorDTO> handleMultipartFileReadException (MultipartFileReadException e) {
        log.error(Constants.MULTIPART_FILE_READ_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(InputStreamFileReadException.class)
    public ResponseEntity<ErrorDTO> handleInputStreamFileReadException (InputStreamFileReadException e) {
        log.error(Constants.INPUT_STREAM_READ_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(InvalidTokenFormatException.class)
    public ResponseEntity<ErrorDTO> handleInvalidTokenFormatException (InvalidTokenFormatException e) {
        log.error(Constants.INVALID_TOKEN_FORMAT_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(PasswordResetAuditTokenInvalidException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenInvalidException (PasswordResetAuditTokenInvalidException e) {
        log.error(Constants.INVALIDATED_PASSWORD_RESET_TOKEN_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(PasswordResetAuditTokenUsedException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenUsedException (PasswordResetAuditTokenUsedException e) {
        log.error(Constants.USED_PASSWORD_RESET_TOKEN_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(PasswordResetAuditTokenNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenNotFoundException (PasswordResetAuditTokenNotFoundException e) {
        log.error(Constants.PASSWORD_RESET_TOKEN_NOT_FOUND_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}