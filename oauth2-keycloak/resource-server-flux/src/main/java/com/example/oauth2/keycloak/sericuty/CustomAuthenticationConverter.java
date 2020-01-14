package com.example.oauth2.keycloak.sericuty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gary Cheng
 */
@Slf4j
public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String REALM_CLAIM_NAME = "realm_access";
    private static final String ROLE_CLAIM_NAME = "roles";
    private static final String USERNAME_CLAIM_NAME = "preferred_username";

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
        Map<String, Object> realm = (Map<String, Object>) jwt.getClaims().get(REALM_CLAIM_NAME);
        Collection<String> authorities = (Collection<String>) realm.get(ROLE_CLAIM_NAME);
        log.debug("extractAuthorities {}", authorities);
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
