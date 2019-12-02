package com.example.authentication.webflux.jwt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfigure {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            RestAuthenticationEntryPoint authenticationEntryPoint,
                                                            RestServerAccessDeniedHandler accessDeniedHandler) {
        log.debug("config SecurityWebFilterChain");
        return http.authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .anyExchange().authenticated()
                .and().logout().disable()
                .securityContextRepository(new WebSessionServerSecurityContextRepository())
                .httpBasic(spec -> spec.authenticationEntryPoint(authenticationEntryPoint))
                .exceptionHandling(spec -> spec.accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}
