package com.example.springapi.web.auth.dto;

import java.util.List;

/**
 * ログイン済みユーザー情報レスポンス DTO。
 */
public record UserInfoResponse(
        String username,
        List<String> roles,
        boolean enabled
) {
}

