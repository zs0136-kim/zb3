package com.example.springapi.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public abstract class BaseApiController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    protected ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(201).body(ApiResponse.ok(data));
    }

    protected ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<ApiResponse<T>> error(String message, String errorCode, int httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.error(message, errorCode));
    }
}
