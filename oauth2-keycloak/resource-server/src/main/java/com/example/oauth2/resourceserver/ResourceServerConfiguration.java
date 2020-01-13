package com.example.oauth2.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

/**
 * @author Gary Cheng
 */
@Configuration
@EnableResourceServer
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnBean(KeycloakAccessTokenConverter.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "exampleResource";
    @Value("${security.oauth2.resource.jwk.key-set-uri}")
    private String jwkSetUri;
    @Autowired
    private KeycloakAccessTokenConverter keycloakAccessTokenConverter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/welcome").permitAll()
                .antMatchers("/api/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwkTokenStore(jwkSetUri, jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(keycloakAccessTokenConverter);
        return converter;
    }
}
