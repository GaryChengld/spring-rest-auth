package com.example.authentication.webflux.jwt.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author Gary Cheng
 */
@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";

    private final String secretKey;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        if (null == token || StringUtils.isEmpty(token)) {
            throw new BadCredentialsException("Invalid token");
        }
        try {
            this.validateToken(token);
        } catch (AuthenticationException e) {
            throw e;
        }
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public void validateToken(String authToken) {
        log.debug("validateToken: {}", authToken);
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
        } catch (JwtException e) {
            throw this.toAuthenticationException(e);
        }
    }

    private AuthenticationException toAuthenticationException(JwtException jwtException) {
        String message;
        if (jwtException instanceof ExpiredJwtException) {
            message = "Expired JWT token";
        } else if (jwtException instanceof UnsupportedJwtException) {
            message = "Unsupported JWT token";
        } else {
            message = "Invalid JWT token";
        }
        return new BadCredentialsException(message);
    }
}
