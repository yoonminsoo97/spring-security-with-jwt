package com.example.springsecurityjwt.global.common.dto;

import com.example.springsecurityjwt.global.error.dto.ErrorResponse;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String SUCCESS_PROPERTY = "data";
    private static final String FAIL_STATUS = "fail";
    private static final String FAIL_PROPERTY = "error";

    private String status;
    private Map<String, T> data;

    private ApiResponse(String status) {
        this.status = status;
        this.data = Collections.emptyMap();
    }

    private ApiResponse(String status, String property, T data) {
        this.status = status;
        this.data = Collections.singletonMap(property, data);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(SUCCESS_STATUS);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, SUCCESS_PROPERTY, data);
    }

    public static ApiResponse<ErrorResponse> fail(ErrorResponse errorResponse) {
        return new ApiResponse<>(FAIL_STATUS, FAIL_PROPERTY, errorResponse);
    }

    @JsonAnyGetter
    public Map<String, T> getData() {
        return data;
    }

}
