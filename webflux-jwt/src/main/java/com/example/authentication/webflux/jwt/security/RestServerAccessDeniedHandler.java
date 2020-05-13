package com.example.authentication.webflux.jwt.security;

import com.example.authentication.webflux.jwt.HttpUtils;
import com.example.authentication.webflux.jwt.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Slf4j
@Component
public class RestServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        log.info(e.getMessage());
        ApiResponse apiResponse = new ApiResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return HttpUtils.writeJsonResponse(exchange.getResponse(), HttpStatus.FORBIDDEN, apiResponse);
    }
}
