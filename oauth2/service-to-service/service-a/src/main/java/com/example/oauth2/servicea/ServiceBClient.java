package com.example.oauth2.servicea;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class ServiceBClient {
    @Value("${service-b.url}")
    private String serviceBUrl;
    @Autowired
    private RestTemplate restTemplate;

    public ApiResponse sendServiceBRequest(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        if (null != authToken) {
            headers.set(HttpHeaders.AUTHORIZATION, authToken);
        }
        HttpEntity entity = new HttpEntity(headers);
        try {
            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    serviceBUrl, HttpMethod.GET, entity, ApiResponse.class);
            return response.getBody();
        } catch (Exception e) {
            return HttpUtils.toApiResponse(e);
        }
    }
}
