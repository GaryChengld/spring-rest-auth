package com.example.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {
    @GetMapping("/welcome")
    public Mono<ApiResponse> home() {
        return Mono.just(new ApiResponse("Public Api"));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Mono<ApiResponse> admin() {
        return Mono.just(new ApiResponse("Admin Api"));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Mono<ApiResponse> user() {
        return Mono.just(new ApiResponse("User Api"));
    }
}
