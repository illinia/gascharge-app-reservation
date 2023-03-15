package com.gascharge.taemin.app.error.exception.web;

import com.gascharge.taemin.app.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
}
