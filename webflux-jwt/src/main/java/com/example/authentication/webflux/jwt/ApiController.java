package com.example.authentication.webflux.jwt;

import com.example.authentication.webflux.jwt.domain.User;
import com.example.authentication.webflux.jwt.dto.ApiResponse;
import com.example.authentication.webflux.jwt.repostory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private UserRepository userRepository;

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
    @PreAuthorize("@customUserDetailsService.canAccessUser(principal, #username)")
    public Mono<ResponseEntity<?>> username(@PathVariable("username") String username) {
        return this.findUserByUsername(username)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(responseEntity(HttpStatus.NOT_FOUND, "User not found")));
    }

    private Mono<User> findUserByUsername(String username) {
        return Mono.create(emmit -> {
            User user = this.userRepository.findByUsername(username);
            if (null != user) {
                emmit.success(user);
            } else {
                emmit.success();
            }
        });
    }

    private ResponseEntity<ApiResponse> responseEntity(HttpStatus httpStatus, String message) {
        ApiResponse apiResponse = new ApiResponse(httpStatus.value(), message);
        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
