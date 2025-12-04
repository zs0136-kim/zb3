package com.example.springapi.common.security.user;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

/**
 * 認証に使用するユーザー情報を表現するモデル。
 */
public class UserAccount {

    /**
     * ユーザー名
     */
    private final String username;
    /**
     * パスワード
     */
    private final String password;
    /**
     * 有効かどうか
     */
    private final boolean enabled;
    private final List<String> roles;
    /**
     * UserAccountのコンストラクタ
     * @param username
     * @param password
     * @param enabled
     * @param roles
     */
    private UserAccount(String username, String password, boolean enabled, List<String> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    /**
     * ユーザー名、パスワード、有効かどうか、ロールを指定してUserAccountを作成
     * @param username
     * @param password
     * @param enabled
     * @param roleCsv
     * @return
     */
    public static UserAccount of(String username, String password, boolean enabled, String roleCsv) {
        List<String> parsedRoles = parseRoles(roleCsv);
        return new UserAccount(username, password, enabled, parsedRoles);
    }

    /**
     * ロールをカンマ区切りの文字列からリストに変換
     * @param roleCsv
     * @return
     */
    private static List<String> parseRoles(String roleCsv) {
        if (!StringUtils.hasText(roleCsv)) {
            return Collections.emptyList();
        }
        return Stream.of(roleCsv.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * ユーザー名を取得
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * パスワードを取得
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 有効かどうかを取得
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * ロールを取得
     * @return
     */
    public List<String> getRoles() {
        return roles;
    }
}

