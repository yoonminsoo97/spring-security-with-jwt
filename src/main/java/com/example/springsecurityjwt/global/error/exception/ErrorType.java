package com.example.springsecurityjwt.global.error.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum ErrorType {

    UN_SUPPORT_ERROR_TYPE(HttpStatus.NOT_FOUND, "E000000", "지원하지 않는 예외 유형입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E400001", "입력값이 잘못되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E401001", "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "E401002", "토큰이 만료되었습니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "E401003", "아이디 또는 비밀번호가 일치하지 않습니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "E409001", "사용 중인 닉네임입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "E049002", "사용 중인 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorType(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorType of(String errorCode) {
        return Arrays.stream(ErrorType.values())
                .filter(errorType -> errorType.errorCode.equals(errorCode))
                .findFirst()
                .orElse(ErrorType.UN_SUPPORT_ERROR_TYPE);
    }

}
