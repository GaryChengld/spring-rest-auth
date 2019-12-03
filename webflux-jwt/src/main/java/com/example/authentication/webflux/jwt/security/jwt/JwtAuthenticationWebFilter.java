package com.example.authentication.webflux.jwt.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

/**
 * @author Gary Cheng
 */
@Slf4j
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.debug("invoked JwtAuthenticationWebFilter");
        Mono<Void> result = super.filter(exchange, chain);
        try {
            Field field = AuthenticationWebFilter.class.getDeclaredField("authenticationFailureHandler");
            field.setAccessible(true);
            ServerAuthenticationFailureHandler authenticationFailureHandler = (ServerAuthenticationFailureHandler) field.get(this);
            WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
            return result.onErrorResume(AuthenticationException.class, (e) -> {
                return authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, e);
            });
        } catch (Exception e) {
            return result;
        }
    }
}
