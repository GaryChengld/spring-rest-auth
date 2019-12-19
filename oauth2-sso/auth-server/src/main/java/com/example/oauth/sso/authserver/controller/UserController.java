package com.example.oauth.sso.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Gary Cheng
 */
@RestController
@Slf4j
public class UserController {
    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        log.debug("user me");
        return principal;
    }
}
