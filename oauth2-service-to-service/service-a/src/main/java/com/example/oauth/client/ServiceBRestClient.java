package com.example.oauth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Gary Cheng
 */
@Component
public class ServiceBRestClient {
    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse sendServiceBRequest(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        if (null != authToken) {
            headers.set(HttpHeaders.AUTHORIZATION, authToken);
        }
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                "http://localhost:8083/api/service-b", HttpMethod.GET, entity, ApiResponse.class);
        return response.getBody();
    }
}
