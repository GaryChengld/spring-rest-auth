package com.example.authentication.dao;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gary Cheng
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ApiResponse home() {
        return new ApiResponse("Public Api");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ApiResponse admin() {
        return new ApiResponse("Admin Api");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ApiResponse user() {
        return new ApiResponse("User Api");
    }
}
