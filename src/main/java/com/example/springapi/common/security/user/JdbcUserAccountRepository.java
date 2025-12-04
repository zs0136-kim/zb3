package com.example.springapi.common.security.user;

import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.springapi.config.SecurityUserProperties;

/**
 * JDBC を利用してユーザー情報を取得するリポジトリ。
 */
@Repository
public class JdbcUserAccountRepository implements UserAccountRepository {

    private static final RowMapper<UserAccount> USER_ROW_MAPPER = (rs, rowNum) ->
            UserAccount.of(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getBoolean("enabled"),
                    rs.getString("roles")
            );

    /**
     * NamedParameterJdbcTemplate
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;
    /**
     * テーブル名
     */
    private final String tableName;

    /**
     * JdbcUserAccountRepositoryのコンストラクタ
     * @param jdbcTemplate
     * @param userProperties
     */
    public JdbcUserAccountRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                     SecurityUserProperties userProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = validateTableName(userProperties.getTable());
    }

    /**
     * ユーザー名からユーザー情報を取得
     * @param username
     * @return
     */
    @Override
    public Optional<UserAccount> findByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("username must not be blank");
        }
        String sql = String.format(
                "SELECT username, password, enabled, roles FROM %s WHERE username = :username",
                tableName
        );
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("username", username));
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, USER_ROW_MAPPER));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    /**
     * テーブル名を検証
     * @param value
     * @return
     */
    private String validateTableName(String value) {
        if (!StringUtils.hasText(value) || !value.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("security.user.table must be alphanumeric/underscore");
        }
        return value;
    }
}

