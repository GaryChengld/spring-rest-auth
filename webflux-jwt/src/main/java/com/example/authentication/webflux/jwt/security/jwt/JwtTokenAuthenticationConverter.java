package com.example.authentication.webflux.jwt.security.jwt;

import com.example.authentication.webflux.jwt.HttpUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Gary Cheng
 */
public class JwtTokenAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String BEARER = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    public JwtTokenAuthenticationConverter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .map(HttpUtils::getAuthTokenFromRequest)
                .filter(Objects::nonNull)
                .filter(authValue -> authValue.startsWith(BEARER))
                .map(authValue -> authValue.substring(BEARER.length(), authValue.length()))
                .map(tokenProvider::getAuthentication);
    }
}
