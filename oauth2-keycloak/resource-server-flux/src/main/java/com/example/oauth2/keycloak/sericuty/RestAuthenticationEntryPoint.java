package com.example.oauth2.keycloak.sericuty;

import com.example.oauth2.keycloak.ApiResponse;
import com.example.oauth2.keycloak.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException authException) {
        log.debug(authException.getMessage());
        ApiResponse apiResponse = new ApiResponse(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        return HttpUtils.writeJsonResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED, apiResponse);
    }
}
