package com.example.springapi.config;

import com.example.springapi.common.security.jwt.JwtAuthenticationEntryPoint;
import com.example.springapi.common.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            "/api/v1/stocks/**"
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
     * APIフィルターチェーンを作成
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
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
