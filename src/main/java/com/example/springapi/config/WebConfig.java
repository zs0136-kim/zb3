package com.example.springapi.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Vue.js との連携を考慮した CORS 設定。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CorsProperties
     */
    private final CorsProperties corsProperties;
    /**
     * WebConfigのコンストラクタ
     * @param corsProperties
     */
    public WebConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    /**
     * CORSマッピングを追加
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> allowedOrigins = corsProperties.getAllowedOrigins();
        // APIエンドポイントに対してCORSを設定
        registry.addMapping("/api/**")
                // 許可するOriginを設定
                .allowedOrigins(allowedOrigins.toArray(String[]::new))
                // 許可するメソッドを設定
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                // 許可するヘッダーを設定
                .allowedHeaders("*")
                // クレデンシャルを許可
                .allowCredentials(true)
                // キャッシュの有効期限を設定
                .maxAge(3600);
    }
}
