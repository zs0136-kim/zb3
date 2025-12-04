package com.example.springapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Pattern;

/**
 * security.user.* 設定。
 */
@Validated
@ConfigurationProperties(prefix = "security.user")
public class SecurityUserProperties {

    // ユーザー情報を保持するテーブル名
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "table name must be alphanumeric with underscore")
    private String table = "user_account";

    // ユーザー情報を保持するテーブル名を取得
    public String getTable() {
        return table;
    }

    // ユーザー情報を保持するテーブル名を設定
    public void setTable(String table) {
        this.table = table;
    }
}

