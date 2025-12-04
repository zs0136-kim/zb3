package com.example.springapi.common.security.user;

import java.util.Optional;

/**
 * ユーザー情報取得のためのリポジトリ。
 */
public interface UserAccountRepository {

    /**
     * ユーザー名からユーザー情報を取得
     * @param username
     * @return
     */
    Optional<UserAccount> findByUsername(String username);
}

