package com.example.springapi.common.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public abstract class BaseApiController {

    /**
     * ログ
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 200 OKを返却
     * @param data
     * @return
     */
    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    /**
     * 200 OKを返却
     * @return
     */
    protected ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    /**
     * 201 Createdを返却
     * @param data
     * @return
     */
    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(201).body(ApiResponse.ok(data));
    }

    /**
     * 204 No Contentを返却
     * @return
     */
    protected ResponseEntity<ApiResponse<Void>> noContent() {
        return ResponseEntity.noContent().build();
    }

    /**
     * エラーを返却
     * @param message
     * @param errorCode
     * @param httpStatus
     * @return
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message, String errorCode, int httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(ApiResponse.error(message, errorCode));
    }
}
