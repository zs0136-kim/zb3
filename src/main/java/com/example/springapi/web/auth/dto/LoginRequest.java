package com.example.springapi.web.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * ログインリクエスト DTO。
 */
public record LoginRequest(
        @NotBlank(message = "username must not be blank")
        String username,
        @NotBlank(message = "password must not be blank")
        String password
) {
}

