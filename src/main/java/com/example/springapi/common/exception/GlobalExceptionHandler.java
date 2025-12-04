package com.example.springapi.common.exception;

import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.common.sftp.SftpClientException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * ビジネス例外のハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
        ErrorCode code = ex.getErrorCode();
        log.warn("ビジネス例外が発生しました: {} - {}", code.getCode(), ex.getMessage());
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), code.getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * バリデーションエラーのハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String errorMessage = errors.values().stream()
                .collect(Collectors.joining(", "));

        log.warn("バリデーションエラーが発生しました: {}", errorMessage);
        ApiResponse<Map<String, String>> body = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .data(errors)
                .message("入力内容に誤りがあります。")
                .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * 認証エラーのハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex) {
        log.warn("認証エラー: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(ex.getMessage(), ErrorCode.UNAUTHORIZED.getCode()));
    }

    /**
     * JWTエラーのハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwt(JwtException ex) {
        log.warn("JWT エラー: {}", ex.getMessage());
        ErrorCode code = ex instanceof ExpiredJwtException
                ? ErrorCode.EXPIRED_TOKEN
                : ErrorCode.INVALID_TOKEN;
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(ApiResponse.error(code.getDefaultMessage(), code.getCode()));
    }

    /**
     * SFTPエラーのハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler(SftpClientException.class)
    public ResponseEntity<ApiResponse<Void>> handleSftp(SftpClientException ex) {
        log.error("SFTP エラー: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ErrorCode.SFTP_OPERATION_FAILED.getDefaultMessage(),
                        ErrorCode.SFTP_OPERATION_FAILED.getCode()));
    }

    /**
     * 予期しないエラーのハンドラ
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOthers(Exception ex) {
        log.error("予期しないエラーが発生しました", ex);
        ApiResponse<Void> body =
                ApiResponse.error("内部エラーが発生しました。", ErrorCode.INTERNAL_ERROR.getCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
