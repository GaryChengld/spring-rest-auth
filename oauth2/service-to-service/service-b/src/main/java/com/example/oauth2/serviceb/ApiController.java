package com.example.oauth2.serviceb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {
    @GetMapping("/service-b")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiResponse service() {
        log.info("Received Service B request");
        return new ApiResponse("Service B");
    }
}
