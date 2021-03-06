package com.example.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @GetMapping("/public")
    public ApiResponse home() {
        return new ApiResponse("Public Api");
    }

    @GetMapping("/user")
    public ApiResponse user() {
        return new ApiResponse("User Api");
    }
}
