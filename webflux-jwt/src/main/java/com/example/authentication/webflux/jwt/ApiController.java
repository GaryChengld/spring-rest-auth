package com.example.authentication.webflux.jwt;

import com.example.authentication.webflux.jwt.domain.User;
import com.example.authentication.webflux.jwt.dto.ApiResponse;
import com.example.authentication.webflux.jwt.dto.AuthRequest;
import com.example.authentication.webflux.jwt.dto.JwtToken;
import com.example.authentication.webflux.jwt.repostory.UserRepository;
import com.example.authentication.webflux.jwt.security.jwt.JwtAuthenticationManager;
import com.example.authentication.webflux.jwt.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ApiController {
    @Autowired
    private JwtAuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Mono<ApiResponse> home() {
        return Mono.just(new ApiResponse("Public Api"));
    }

    @PostMapping("/signin")
    public Mono<ResponseEntity<?>> authorize(@Valid @RequestBody AuthRequest authRequest) {
        log.debug("authorize authRequest:{}", authRequest);
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        return this.authenticationManager.authenticate(authenticationToken)
                .doOnSuccess($ -> ReactiveSecurityContextHolder.withAuthentication(authenticationToken))
                .map(tokenProvider::createToken)
                .map(JwtToken::new)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(responseEntity(HttpStatus.UNAUTHORIZED, e.getMessage())));
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
