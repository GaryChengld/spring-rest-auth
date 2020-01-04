package com.example.oauth2.jwt.jwk.authserver.controller;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Gary Cheng
 */
@RestController
@Slf4j
public class JwkSetController {
    @Autowired
    private JWKSet jwkSet;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        log.debug("Received jwks request");
        return this.jwkSet.toJSONObject();
    }
}
