package com.example.springapi.web.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * リフレッシュトークン再発行用 DTO。
 */
public record RefreshTokenRequest(
        @NotBlank(message = "refreshToken must not be blank")
        String refreshToken
) {
}

