package com.example.springapi.config;

import com.example.springapi.common.security.jwt.JwtAuthenticationEntryPoint;
import com.example.springapi.common.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * セキュリティ設定。
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * 認証不要なエンドポイント
     */
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/refresh",
            "/api/public/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            
            // 在庫関連エンドポイント
            "/api/v1/stocks/**",
            // 出庫関連エンドポイント
            "/api/v1/sends/**"
    };

    /**
     * JwtAuthenticationFilter
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    /**
     * JwtAuthenticationEntryPoint
     */
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * SecurityConfigのコンストラクタ
     * @param jwtAuthenticationFilter
     * @param jwtAuthenticationEntryPoint
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    /**
     * Graph API向けのOAuth2ログイン用フィルターチェーン。
     * セッションを必要に応じて作成し、ブラウザ経由の認可コードフローを許可する。
     */
    @Bean
    @Order(0)
    public SecurityFilterChain graphOAuth2FilterChain(HttpSecurity http) throws Exception {
        http
                // Graph API向けのOAuth2ログイン用フィルターチェーン
                .securityMatcher("/graph/**", "/oauth2/**", "/login/oauth2/**")
                // CSRFを無効化
                .csrf(csrf -> csrf.disable())
                // CORSを有効化
                .cors(Customizer.withDefaults())
                // セッション管理を必要に応じて作成
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // 認証が必要なエンドポイント
                .authorizeHttpRequests(auth -> auth
                        // Graph API向けのエンドポイント
                        .requestMatchers("/graph/**").authenticated()
                        // それ以外のエンドポイントは認証不要
                        .anyRequest().permitAll()
                )
                // OAuth2ログインを有効化
                .oauth2Login(Customizer.withDefaults());

        // フィルターチェーンをビルドして返す
        return http.build();
    }

    /**
     * APIフィルターチェーンを作成
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 認証不要なエンドポイント
                .authorizeHttpRequests(auth -> auth
                        // 認証不要なエンドポイント
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        // SFTP関連エンドポイントはADMINまたはSFTPロールを持つユーザーのみアクセス可能
                        .requestMatchers("/api/sftp/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SFTP")
                        // それ以外のエンドポイントは認証必須
                        .anyRequest().authenticated()
                )
                // 認証失敗時のエラーハンドリング
                .exceptionHandling(handler -> handler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // JWT認証フィルターをUsernamePasswordAuthenticationFilterの前に追加
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // OAuth2 リソースサーバー設定（必要になった際に有効化）
        // http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * PasswordEncoderを作成
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
