package com.example.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfigure {
    @Value("${oauth2.checkTokenUrl}")
    private String checkTokenUrl;
    @Value("${oauth2.clientId}")
    private String clientId;
    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.debug("config SecurityWebFilterChain");
        http.httpBasic().disable()
                .authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers("/api/**").authenticated()
                .and().oauth2ResourceServer()
                    .opaqueToken().introspectionUri(checkTokenUrl).introspectionClientCredentials(clientId, clientSecret);
        return http.build();
    }
}
