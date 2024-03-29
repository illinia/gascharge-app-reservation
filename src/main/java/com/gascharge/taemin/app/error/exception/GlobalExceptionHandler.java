package com.gascharge.taemin.app.error.exception;

import com.gascharge.taemin.app.error.errorcode.CommonErrorCode;
import com.gascharge.taemin.app.error.errorcode.ErrorCode;
import com.gascharge.taemin.app.error.errorcode.UserErrorCode;
import com.gascharge.taemin.app.error.exception.web.RestApiException;
import com.gascharge.taemin.common.exception.jpa.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final HandleException handleException;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException ", e);
        return handleException.handleExceptionInternal(UserErrorCode.INACTIVE_USER, UserErrorCode.INACTIVE_USER.getMessage());
    }

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity handleNoEntityFoundException(NoEntityFoundException e) {
        return handleException.handleExceptionInternal(CommonErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleDuplicateKeyException(DuplicateKeyException e) {
        return handleException.handleExceptionInternal(CommonErrorCode.RESOURCE_CONFLICT, e.getMessage());
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity handleCustomException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleException.handleExceptionInternal(errorCode, errorCode.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgument(IllegalArgumentException e) {
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleException.handleExceptionInternal(errorCode, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorCode invalidParameter = CommonErrorCode.INVALID_PARAMETER;
        return handleException.handleExceptionInternal(invalidParameter, ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(String.valueOf(ex), headers, status, request);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleException.handleExceptionInternal(ex, errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleAllException(Exception ex) {
        log.error("handleAllException ", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleException.handleExceptionInternal(errorCode, ex.getMessage());
    }
}
