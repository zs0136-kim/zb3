package com.example.springapi.graph.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Graph API の /me を確認するための簡易エンドポイント。
 * ブラウザでログイン→同意後、アクセストークンで /me を呼び出す。
 */
@RestController
@RequestMapping("/graph")
public class GraphMeController {

    private static final Logger log = LoggerFactory.getLogger(GraphMeController.class);
    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private final WebClient graphWebClient;

    public GraphMeController(WebClient graphWebClient) {
        this.graphWebClient = graphWebClient;
    }

    /**
     * /me 呼び出し。認可されていなければ Spring Security が自動でログイン画面へリダイレクトする。
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient authorizedClient) {
        // 防御的にトークンを確認
        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            log.warn("Graph OAuth2 authorized client missing or token absent");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("access token not available");
        }

        try {
            Map<String, Object> body = graphWebClient
                    .get()
                    .uri("https://graph.microsoft.com/v1.0/me")
                    .retrieve()
                    .bodyToMono(MAP_TYPE)
                    .block();
            return ResponseEntity.ok(body);
        } catch (WebClientResponseException ex) {
            log.error("Graph /me call failed. status:{} body:{}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
            return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Graph /me call unexpected error", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("graph call failed");
        }
    }
}
