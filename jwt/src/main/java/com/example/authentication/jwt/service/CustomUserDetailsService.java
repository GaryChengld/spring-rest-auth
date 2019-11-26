package com.example.authentication.jwt.service;

import com.example.authentication.jwt.domain.User;
import com.example.authentication.jwt.repostory.UserRepository;
import com.example.authentication.jwt.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Gary Cheng
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    public boolean canAccessUser(org.springframework.security.core.userdetails.User currentUser, String username) {
        log.debug("Checking if {} has access to {}", currentUser.getUsername(), username);
        return currentUser != null
                && (currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN) || currentUser.getUsername().equals(username)));
    }
}
