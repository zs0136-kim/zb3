package com.example.springapi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * cors.* 設定。
 */
@Validated
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private final List<String> allowedOrigins = new ArrayList<>(List.of("http://localhost:5173"));

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }
}

