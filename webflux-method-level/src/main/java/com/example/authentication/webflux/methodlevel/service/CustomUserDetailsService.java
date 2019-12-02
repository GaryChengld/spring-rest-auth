package com.example.authentication.webflux.methodlevel.service;

import com.example.authentication.webflux.methodlevel.domain.User;
import com.example.authentication.webflux.methodlevel.repostory.UserRepository;
import com.example.authentication.webflux.methodlevel.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Gary Cheng
 */
@Service
@Slf4j
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.create(emmit -> {
            User user = userRepository.findByUsername(username);
            if (null == user) {
                emmit.error(new UsernameNotFoundException("User Not Found"));
            } else {
                emmit.success(new UserPrincipal(user));
            }
        });
    }

    public boolean canAccessUser(org.springframework.security.core.userdetails.User currentUser, String username) {
        log.debug("Checking if {} has access to {}", currentUser.getUsername(), username);
        return currentUser != null
                && (currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ROLE_ADMIN) || currentUser.getUsername().equals(username)));
    }
}
