package com.example.authentication.webflux.jwt.security;

import com.example.authentication.webflux.jwt.security.jwt.*;
import com.example.authentication.webflux.jwt.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfigure {
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestServerAccessDeniedHandler accessDeniedHandler;

    public SecurityConfigure(CustomUserDetailsService userDetailsService,
                             JwtTokenProvider tokenProvider,
                             RestAuthenticationEntryPoint authenticationEntryPoint,
                             RestServerAccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.debug("config SecurityWebFilterChain");
        http.httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable();
        http.authorizeExchange()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/signin").permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and().addFilterAt(jwtWebFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    public AuthenticationWebFilter jwtWebFilter() {
        AuthenticationWebFilter filter = new JwtAuthenticationWebFilter(jwtAuthenticationManager());
        filter.setServerAuthenticationConverter(new JwtTokenAuthenticationConverter(tokenProvider));
        filter.setRequiresAuthenticationMatcher(new JwtHeadersExchangeMatcher());
        filter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint));
        return filter;
    }

    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(userDetailsService, passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
