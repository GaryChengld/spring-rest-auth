package com.example.oauth2.keycloak.sericuty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfigure {
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestServerAccessDeniedHandler restAccessDeniedHandler;

    public SecurityConfigure(RestAuthenticationEntryPoint restAuthenticationEntryPoint, RestServerAccessDeniedHandler restAccessDeniedHandler) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.debug("config SecurityWebFilterChain");
        http.httpBasic().disable()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers("/api/**").authenticated()
                .and()
                .oauth2ResourceServer(configurer -> configurer.authenticationEntryPoint(restAuthenticationEntryPoint)
                        .jwt()
                        .jwtAuthenticationConverter(this.jwtAuthenticationConverter()));
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return new ReactiveJwtAuthenticationConverterAdapter(new CustomAuthenticationConverter());
    }
}
