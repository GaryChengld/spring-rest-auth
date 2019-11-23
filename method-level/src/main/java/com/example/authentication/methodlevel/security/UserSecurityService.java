package com.example.authentication.methodlevel.security;

import com.example.authentication.methodlevel.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Gary Cheng
 */
@Service
@Slf4j
public class UserSecurityService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public boolean canAccessUser(UserPrincipal currentUser, String username) {
        log.debug("Checking if user={} has access to user={}", currentUser, username);
        return currentUser != null
                && (currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN) || currentUser.getUsername().equals(username)));
    }
}
