package com.example.oauth2.keycloak;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().oauth2Login()
                .and().logout().deleteCookies().invalidateHttpSession(true).logoutSuccessUrl("/").permitAll();
    }
}
