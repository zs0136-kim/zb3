package com.example.springapi.web.auth;

import java.util.List;

import com.example.springapi.common.security.jwt.JwtTokenProvider;
import com.example.springapi.common.security.jwt.JwtUserDetailsService;
import com.example.springapi.config.JwtProperties;
import com.example.springapi.web.auth.dto.LoginRequest;
import com.example.springapi.web.auth.dto.RefreshTokenRequest;
import com.example.springapi.web.auth.dto.TokenResponse;
import com.example.springapi.web.auth.dto.UserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 認証関連のアプリケーションサービス。
 */
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    public AuthService(JwtTokenProvider jwtTokenProvider,
                       JwtUserDetailsService jwtUserDetailsService,
                       PasswordEncoder passwordEncoder,
                       JwtProperties jwtProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties;
    }

    public TokenResponse login(LoginRequest request) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            log.warn("Invalid credentials for user {}", request.username());
            throw new BadCredentialsException("Invalid username or password");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails.getUsername());
        log.info("User {} logged in successfully", request.username());
        return buildTokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (!jwtTokenProvider.isTokenValid(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String username = jwtTokenProvider.extractUsername(refreshToken);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);
        log.info("Issued new token pair for user {}", username);
        return buildTokenResponse(newAccessToken, newRefreshToken);
    }

    public UserInfoResponse buildUserInfo(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .filter(StringUtils::hasText)
                .toList();
        return new UserInfoResponse(userDetails.getUsername(), roles, userDetails.isEnabled());
    }

    public void logout(String username) {
        log.info("Logout requested for user {}", username);
    }

    private TokenResponse buildTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.of(
                accessToken,
                refreshToken,
                jwtProperties.getAccessTokenExpiration(),
                jwtProperties.getRefreshTokenExpiration()
        );
    }
}

