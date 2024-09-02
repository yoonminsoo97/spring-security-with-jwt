package com.example.springsecurityjwt.global.error.handler;

import com.example.springsecurityjwt.global.common.dto.ApiResponse;
import com.example.springsecurityjwt.global.error.dto.ErrorResponse;
import com.example.springsecurityjwt.global.error.exception.BaseException;
import com.example.springsecurityjwt.global.error.exception.ErrorType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handle(BaseException e) {
        final ErrorType errorType = e.getErrorType();
        final ErrorResponse errorResponse = ErrorResponse.of(errorType);
        return ResponseEntity.status(errorType.getHttpStatus()).body(ApiResponse.fail(errorResponse));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handle(MethodArgumentNotValidException e) {
        final ErrorType errorType = ErrorType.INVALID_INPUT_VALUE;
        final ErrorResponse errorResponse = ErrorResponse.of(errorType, e.getBindingResult());
        return ResponseEntity.badRequest().body(ApiResponse.fail(errorResponse));
    }

}
