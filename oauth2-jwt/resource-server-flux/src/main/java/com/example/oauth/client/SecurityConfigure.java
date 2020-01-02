package com.example.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.interfaces.RSAPublicKey;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfigure {
    @Value("${jwt.key.location}")
    private RSAPublicKey key;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.debug("config SecurityWebFilterChain");
        http.httpBasic().disable()
                .authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers("/api/**").authenticated()
                .and().oauth2ResourceServer()
                .jwt().jwtDecoder(jwtDecoder());
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        log.info(key.toString());
        return NimbusReactiveJwtDecoder.withPublicKey(this.key).build();
    }
}
