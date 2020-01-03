package com.example.oauth2.jwk.secretkey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {
    @GetMapping("/welcome")
    public ApiResponse home() {
        return new ApiResponse("Public Api");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiResponse admin() {
        return new ApiResponse("Admin Api");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponse user() {
        return new ApiResponse("User Api");
    }

    @GetMapping("/payload")
    public String payload(Authentication authentication) {
        OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        log.info(authenticationDetails.getTokenValue());
        Jwt jwt = JwtHelper.decode(authenticationDetails.getTokenValue());
        return jwt.getClaims();
    }
}
