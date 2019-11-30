package com.example.authentication.webflux.methodlevel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ApiController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Mono<ApiResponse> home() {
        return Mono.just(new ApiResponse("Public Api"));
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public Mono<ApiResponse> admin() {
        return Mono.just(new ApiResponse("Admin Api"));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Mono<ApiResponse> user() {
        return Mono.just(new ApiResponse("User Api"));
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @PreAuthorize("@userSecurityService.canAccessUser(principal, #username)")
    public ApiResponse username(@PathVariable("username") String username) {
        return new ApiResponse(username);
    }
}
