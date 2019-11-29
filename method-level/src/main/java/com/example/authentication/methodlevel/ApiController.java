package com.example.authentication.methodlevel;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("permitAll()")
    public ApiResponse home() {
        return new ApiResponse("Public Api");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiResponse admin() {
        return new ApiResponse("Admin Api");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiResponse user() {
        return new ApiResponse("User Api");
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @PreAuthorize("@userSecurityService.canAccessUser(principal, #username)")
    public ApiResponse username(@PathVariable("username") String username) {
        return new ApiResponse(username);
    }
}
