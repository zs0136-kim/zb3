package com.example.springapi.web.auth.dto;

/**
 * アクセストークンとリフレッシュトークンのレスポンス DTO。
 */
public record TokenResponse(
        String accessToken,
        String refreshToken,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn,
        String tokenType
) {

    public static TokenResponse of(String accessToken,
                                   String refreshToken,
                                   long accessTokenExpiresIn,
                                   long refreshTokenExpiresIn) {
        return new TokenResponse(accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn, "Bearer");
    }
}

