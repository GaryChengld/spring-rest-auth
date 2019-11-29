package com.example.authentication.webflux.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfigure {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/h2/**").permitAll()
                .pathMatchers("/api/welcome").permitAll()
                .pathMatchers("/api/user").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/admin").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().csrf().disable()
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER", "ADMIN").build();
        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}
