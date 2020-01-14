package com.example.oauth2.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Gary Cheng
 */
public class HttpUtils {
    private HttpUtils() {
    }

    private static ObjectMapper mapper = new ObjectMapper();

    public static Mono<Void> writeJsonResponse(ServerHttpResponse response, HttpStatus httpStatus, Object body) {
        try {
            response.setStatusCode(httpStatus);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        } catch (JsonProcessingException e) {
            return Mono.empty();
        }
    }
}
