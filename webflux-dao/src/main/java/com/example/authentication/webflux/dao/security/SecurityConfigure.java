package com.example.authentication.webflux.dao.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Slf4j
public class SecurityConfigure {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            RestAuthenticationEntryPoint authenticationEntryPoint,
                                                            RestServerAccessDeniedHandler accessDeniedHandler) {
        log.debug("config SecurityWebFilterChain");
        return http.authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers("/api/user").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/admin").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().securityContextRepository(new WebSessionServerSecurityContextRepository())
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
