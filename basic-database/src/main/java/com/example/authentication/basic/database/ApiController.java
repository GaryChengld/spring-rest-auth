package com.example.authentication.basic.database;

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
    public Message home() {
        return new Message("Public Api");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Message admin() {
        return new Message("Admin Api");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Message user() {
        return new Message("User Api");
    }
}
