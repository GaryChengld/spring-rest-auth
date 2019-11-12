package com.example.authentication.reactive.basic;

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
    public Mono<Message> home() {
        return Mono.just(new Message("Public Api"));
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Mono<Message> admin() {
        return Mono.just(new Message("Admin Api"));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Mono<Message> user() {
        return Mono.just(new Message("User Api"));
    }
}
