package com.example.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {
    @Autowired
    private ServiceBClient serviceBClient;

    @GetMapping("/service-a")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponse service(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        ApiResponse apiResponse = new ApiResponse("Service A");
        ApiResponse apiBResponse = serviceBClient.sendServiceBRequest(authToken);
        apiResponse.setData(apiBResponse);
        return apiResponse;
    }
}
