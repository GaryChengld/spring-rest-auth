package com.example.authentication.userservice;

import com.example.authentication.userservice.domain.Response;
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
    public Response home() {
        return new Response("Public Api");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Response admin() {
        return new Response("Admin Api");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Response user() {
        return new Response("User Api");
    }
}
