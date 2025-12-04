package com.example.springapi.web.auth;

import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.web.auth.dto.LoginRequest;
import com.example.springapi.web.auth.dto.RefreshTokenRequest;
import com.example.springapi.web.auth.dto.TokenResponse;
import com.example.springapi.web.auth.dto.UserInfoResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 認証関連 API コントローラー。
 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.ok(authService.refresh(request));
    }

    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> me(Authentication authentication) {
        UserDetails userDetails = extractUserDetails(authentication);
        return ApiResponse.ok(authService.buildUserInfo(userDetails));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        UserDetails userDetails = extractUserDetails(authentication);
        authService.logout(userDetails.getUsername());
        log.info("User {} logged out", userDetails.getUsername());
        return ApiResponse.ok(null);
    }

    private UserDetails extractUserDetails(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails;
        }
        throw new AuthenticationCredentialsNotFoundException("Invalid authentication principal");
    }
}

