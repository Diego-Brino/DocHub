package com.dochub.api.handlers;

import com.dochub.api.dtos.ErrorDTO;
import com.dochub.api.exceptions.*;
import com.dochub.api.exceptions.s3.*;
import com.dochub.api.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
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

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundByEmailException (UserNotFoundByEmailException e) {
        log.error(Constants.USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationException (AuthenticationException e) {
        log.error(Constants.USER_NOT_FOUND_BY_EMAIL_EXCEPTION_MESSAGE, e);

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

    @ExceptionHandler(InvalidPasswordRecoveryTokenException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenInvalidException (InvalidPasswordRecoveryTokenException e) {
        log.error(Constants.INVALID_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UsedPasswordRecoveryTokenException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenUsedException (UsedPasswordRecoveryTokenException e) {
        log.error(Constants.USED_PASSWORD_RECOVERY_TOKEN_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(PasswordRecoveryTokenNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePasswordResetAuditTokenNotFoundException (PasswordRecoveryTokenNotFoundException e) {
        log.error(Constants.PASSWORD_RECOVERY_TOKEN_NOT_FOUND_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorDTO> handlePasswordMismatchException (PasswordMismatchException e) {
        log.error(Constants.PASSWORD_MISMATCH_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(InvalidUsernameFormatException.class)
    public ResponseEntity<ErrorDTO> handleInvalidUsernameFormatException (InvalidUsernameFormatException e) {
        log.error(Constants.INVALID_USERNAME_FORMAT_EXCEPTION_MESSAGE, e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorDTO> handlePermissionDeniedException (PermissionDeniedException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(RoleCannotBeDeletedException.class)
    public ResponseEntity<ErrorDTO> handleRoleCannotBeDeletedException (RoleCannotBeDeletedException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(CannotDeleteOwnUserException.class)
    public ResponseEntity<ErrorDTO> handleCannotDeleteOwnUserException (CannotDeleteOwnUserException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(CannotCreateAdminRoleException.class)
    public ResponseEntity<ErrorDTO> handleCannotCreateAdminRoleException (CannotCreateAdminRoleException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(CannotDeleteAdminRoleException.class)
    public ResponseEntity<ErrorDTO> handleCannotDeleteAdminRoleException (CannotDeleteAdminRoleException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(CannotEditAdminRoleException.class)
    public ResponseEntity<ErrorDTO> handleCannotEditAdminRoleException (CannotEditAdminRoleException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<ErrorDTO> handleInvalidFileTypeException (InvalidFileTypeException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException (EntityNotFoundException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(InputStreamReadException.class)
    public ResponseEntity<ErrorDTO> handleInputStreamReadException (InputStreamReadException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(InvalidFolderMoveException.class)
    public ResponseEntity<ErrorDTO> handleInvalidFolderMoveException (InvalidFolderMoveException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ArchiveAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleArchiveAlreadyExistsException (ArchiveAlreadyExistsException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ActivityInUseException.class)
    public ResponseEntity<ErrorDTO> handleActivityInUseException (ActivityInUseException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ProcessInProgressException.class)
    public ResponseEntity<ErrorDTO> handleProcessInProgressException (ProcessInProgressException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ResponseInUseException.class)
    public ResponseEntity<ErrorDTO> handleResponseInUseException (ResponseInUseException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ServiceHasProcessesInProgressException.class)
    public ResponseEntity<ErrorDTO> handleServiceHasProcessesInProgressException (ServiceHasProcessesInProgressException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(FlowWithOrderOneAlreadyRegisterException.class)
    public ResponseEntity<ErrorDTO> handleFlowWithOrderOneAlreadyRegisterException (FlowWithOrderOneAlreadyRegisterException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ProcessAlreadyStartedException.class)
    public ResponseEntity<ErrorDTO> handleProcessAlreadyStartedException (ProcessAlreadyStartedException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(BucketAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleBucketAlreadyExistsException (BucketAlreadyExistsException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(BucketNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleBucketNotFoundException (BucketNotFoundException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(CreateBucketException.class)
    public ResponseEntity<ErrorDTO> handleCreateBucketException (CreateBucketException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DeleteBucketException.class)
    public ResponseEntity<ErrorDTO> handleDeleteBucketException (DeleteBucketException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(DeleteObjectException.class)
    public ResponseEntity<ErrorDTO> handleDeleteObjectException (DeleteObjectException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleObjectNotFoundException (ObjectNotFoundException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(PresignedUrlGenerationException.class)
    public ResponseEntity<ErrorDTO> handlePresignedUrlGenerationException (PresignedUrlGenerationException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(GetS3ObjectException.class)
    public ResponseEntity<ErrorDTO> handleGetS3ObjectException (GetS3ObjectException e) {
        log.error(e.getMessage(), e);

        final ErrorDTO errorDTO = new ErrorDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}