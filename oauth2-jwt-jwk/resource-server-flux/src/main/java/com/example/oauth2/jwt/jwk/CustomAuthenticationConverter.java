package com.example.oauth2.jwt.jwk;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Gary Cheng
 */
public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String AUTHORITIES_CLAIM_NAME = "authorities";
    private static final String USERNAME_CLAIM_NAME = "user_name";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String name = this.extractName(jwt);
        Collection<GrantedAuthority> authorities = this.extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, name);
    }

    private String extractName(Jwt jwt) {
        String name = jwt.getSubject();
        if (!StringUtils.hasText(name)) {
            name = jwt.getClaimAsString(USERNAME_CLAIM_NAME);
        }
        return name;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> authorities = (Collection<String>) jwt.getClaims().get(AUTHORITIES_CLAIM_NAME);
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
