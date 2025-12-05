package com.example.springapi.common.security.jwt;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT を用いた認証フィルター。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * JwtTokenProvider
     */
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * JwtUserDetailsService
     */
    private final JwtUserDetailsService jwtUserDetailsService;
    /**
     * 除外パスのリスト
     */
    private final List<RequestMatcher> excludedMatchers;

    /**
     * JwtAuthenticationFilterのコンストラクタ
     * @param jwtTokenProvider
     * @param jwtUserDetailsService
     */
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.excludedMatchers = List.of(
                new AntPathRequestMatcher("/api/auth/login"),
                new AntPathRequestMatcher("/api/auth/refresh"),
                new AntPathRequestMatcher("/api/auth/logout"),
                new AntPathRequestMatcher("/api/public/**"),

                // Swagger関連エンドポイント
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/swagger-resources/**"),

                // テスト用除外
                // 在庫関連エンドポイント
                new AntPathRequestMatcher("/api/v1/stocks/**"),
                // 出庫関連エンドポイント
                new AntPathRequestMatcher("/api/v1/sends/**")
        );
    }

    /**
     * JWT認証を行う
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.isTokenValid(token)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtTokenProvider.extractUsername(token);
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            log.warn("JWT authentication failed: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 認証を行わないパスを判定
     * @param request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        return excludedMatchers.stream().anyMatch(matcher -> matcher.matches(request));
    }

    /**
     * トークンを解決
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return header.substring(BEARER_PREFIX.length());
    }
}

