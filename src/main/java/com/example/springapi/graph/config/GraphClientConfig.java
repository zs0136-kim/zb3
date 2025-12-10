package com.example.springapi.graph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Graph API呼び出しに必要なWebClientとトークン管理の設定。
 * 認可コードフローとリフレッシュトークンでアクセストークンを維持する。
 */
@Configuration
public class GraphClientConfig {

    /**
     * OAuth2AuthorizedClientManagerを作成する。
     * @param clientRegistrationRepository
     * @param authorizedClientService
     * @return
     */
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            // クライアント登録リポジトリ
            ClientRegistrationRepository clientRegistrationRepository,
            // 認可クライアントサービス
            OAuth2AuthorizedClientService authorizedClientService) {

        // 認可クライアントプロバイダー
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .build();

        // 認可クライアントマネージャー
        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientService);
        // 認可クライアントプロバイダーを設定
        manager.setAuthorizedClientProvider(authorizedClientProvider);
        // 認可クライアントマネージャーを返す
        return manager;
    }

    /**
     * Graph API呼び出しに必要なWebClientを作成する。
     * @param authorizedClientManager
     * @return
     */
    @Bean
    public WebClient graphWebClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        // 認可クライアントフィルター
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Filter =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        // デフォルトクライアント登録IDを設定
        oauth2Filter.setDefaultClientRegistrationId("graph");
        // デフォルト認可クライアントを設定
        oauth2Filter.setDefaultOAuth2AuthorizedClient(true);

        // WebClientを返す
        return WebClient.builder()
                // 認可クライアントフィルターを適用
                .apply(oauth2Filter.oauth2Configuration())
                .build();
    }
}
