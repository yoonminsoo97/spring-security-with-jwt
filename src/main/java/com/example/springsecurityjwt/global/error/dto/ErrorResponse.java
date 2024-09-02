package com.example.springsecurityjwt.global.error.dto;

import com.example.springsecurityjwt.global.error.exception.ErrorType;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Field> fields;

    private ErrorResponse(ErrorType errorType) {
        this.code = errorType.getErrorCode();
        this.message = errorType.getMessage();
        this.fields = Collections.emptyList();
    }

    private ErrorResponse(ErrorType errorType, BindingResult bindingResult) {
        this.code = errorType.getErrorCode();
        this.message = errorType.getMessage();
        this.fields = Field.errors(bindingResult);
    }

    public static ErrorResponse of(ErrorType errorType) {
        return new ErrorResponse(errorType);
    }

    public static ErrorResponse of(ErrorType errorType, BindingResult bindingResult) {
        return new ErrorResponse(errorType, bindingResult);
    }

    @Getter
    private static class Field {

        private String field;
        private String input;
        private String message;

        private Field(String field, String input, String message) {
            this.field = field;
            this.input = input;
            this.message = message;
        }

        public static List<Field> errors(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new Field(
                            fieldError.getField(),
                            fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                            fieldError.getDefaultMessage()
                    )).collect(Collectors.toList());
        }

    }

}
