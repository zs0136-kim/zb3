package com.example.springapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI メタ情報設定
     * 在庫管理ERP用API全体のタイトルや説明を定義する
     */
    @Bean
    public OpenAPI inventoryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Z-EBIS3 在庫管理 API") // APIタイトル
                        .description("Z-EBIS3 在庫管理ERP向け REST API ドキュメント") // API全体の説明
                        .version("v1") // バージョン
                        .license(new License().name("Proprietary").url("https://example.com"))); // ライセンス情報（ダミー）
    }
}


