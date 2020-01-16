package com.example.oauth2.okta.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Gary Cheng
 */
@Slf4j
public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String GROUPS_CLAIM_NAME = "groups";
    private static final String USERNAME_CLAIM_NAME = "sub";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String name = this.extractName(jwt);
        Collection<GrantedAuthority> authorities = this.extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, name);
    }

    private String extractName(Jwt jwt) {
        String name = jwt.getClaimAsString(USERNAME_CLAIM_NAME);
        log.debug("extractName {}", name);
        return name;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> groups = (Collection<String>) jwt.getClaims().get(GROUPS_CLAIM_NAME);
        log.debug("extractAuthorities {}", groups);
        return groups.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
