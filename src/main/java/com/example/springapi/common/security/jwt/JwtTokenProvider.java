package com.example.springapi.common.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import com.example.springapi.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * JWT 토큰 생성/검증을 담당하는 컴포넌트.
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String CLAIM_AUTHORITIES = "roles";
    private static final String CLAIM_TOKEN_TYPE = "tokenType";
    private static final String TOKEN_TYPE_REFRESH = "refresh";
    private static final int MIN_SECRET_LENGTH = 32;

    /**
     * JwtProperties
     */
    private final JwtProperties jwtProperties;
    /**
     * SecretKey
     */
    private final SecretKey secretKey;

    /**
     * JwtTokenProviderのコンストラクタ
     * @param jwtProperties
     */
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = initSecretKey(jwtProperties.getSecret());
    }

    /**
     * Access Tokenを生成
     * @param userDetails
     * @return
     */
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        if (!CollectionUtils.isEmpty(authorities)) {
            claims.put(CLAIM_AUTHORITIES, authorities);
        }
        return createToken(userDetails.getUsername(), claims, jwtProperties.getAccessTokenExpiration());
    }

    /**
     * Refresh Tokenを生成
     * @param username
     * @return
     */
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = Map.of(CLAIM_TOKEN_TYPE, TOKEN_TYPE_REFRESH);
        return createToken(username, claims, jwtProperties.getRefreshTokenExpiration());
    }

    /**
     * トークンが有効かどうかを判定
     * @param token
     * @return
     */
    public boolean isTokenValid(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Invalid JWT token: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * トークンを解析
     * @param token
     * @return
     */
    public Claims parseClaims(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("Token must not be blank");
        }
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * トークンからユーザー名を抽出
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Refresh Tokenかどうかを判定
     * @param token
     * @return
     */
    public boolean isRefreshToken(String token) {
        Object tokenType = parseClaims(token).get(CLAIM_TOKEN_TYPE);
        return TOKEN_TYPE_REFRESH.equals(tokenType);
    }

    /**
     * SecretKeyを初期化
     * @param rawSecret
     * @return
     */
    private SecretKey initSecretKey(String rawSecret) {
        if (!StringUtils.hasText(rawSecret)) {
            throw new IllegalArgumentException("JWT secret must not be blank");
        }
        byte[] keyBytes = rawSecret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < MIN_SECRET_LENGTH) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * トークンを生成
     * @param subject
     * @param additionalClaims
     * @param validityInMillis
     * @return
     */
    private String createToken(String subject, Map<String, Object> additionalClaims, long validityInMillis) {
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("Subject must not be blank");
        }
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(validityInMillis);
        Map<String, Object> claims = new HashMap<>();
        if (additionalClaims != null) {
            claims.putAll(additionalClaims);
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}

