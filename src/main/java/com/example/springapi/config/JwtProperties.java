package com.example.springapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * JWT設定値を保持するプロパティクラ設定設定
 */
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Secret
     */
    @NotBlank
    private String secret;

    /**
     * Access Token Expiration
     */
    @Positive
    private long accessTokenExpiration;

    /**
     * Refresh Token Expiration
     */
    @Positive
    private long refreshTokenExpiration;

    /**
     * Issuer
     */
    private String issuer = "spring-api";

    /**
     * Secretを取得
     * @return
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Secretを設定
     * @param secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Access Token Expirationを取得
     * @return
     */
    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    /**
     * Access Token Expirationを設定
     * @param accessTokenExpiration
     */
    public void setAccessTokenExpiration(long accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    /**
     * Refresh Token Expirationを取得
     * @return
     */
    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    /**
     * Refresh Token Expirationを設定
     * @param refreshTokenExpiration
     */
    public void setRefreshTokenExpiration(long refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    /**
     * Issuerを取得
     * @return
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Issuerを設定
     * @param issuer
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}

