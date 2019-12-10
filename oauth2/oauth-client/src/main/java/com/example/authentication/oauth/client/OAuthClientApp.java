package com.example.authentication.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
//@EnableOAuth2Client
public class OAuthClientApp {

	public static void main(String[] args) {
		SpringApplication.run(OAuthClientApp.class, args);
	}

}
