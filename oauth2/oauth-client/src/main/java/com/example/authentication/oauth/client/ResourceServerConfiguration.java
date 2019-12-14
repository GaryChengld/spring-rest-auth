package com.example.authentication.oauth.client;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableResourceServer
@Order(5)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/welcome").permitAll()
                .antMatchers("/api/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/api/**").authenticated();
    }

    @Primary
    @Bean
    public UserInfoTokenServices tokenService() {
        final UserInfoTokenServices tokenService = new UserInfoTokenServices("http://localhost:8081/auth/user/me", "R2dpxQ3vPrtfgF72");
        return tokenService;
    }
}
