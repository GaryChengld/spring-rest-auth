package com.example.authentication.webflux.dao;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Mono<ApiResponse> home() {
        return Mono.just(new ApiResponse("Public Api"));
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Mono<ApiResponse> admin() {
        return Mono.just(new ApiResponse("Admin Api"));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Mono<ApiResponse> user() {
        return Mono.just(new ApiResponse("User Api"));
    }
}
