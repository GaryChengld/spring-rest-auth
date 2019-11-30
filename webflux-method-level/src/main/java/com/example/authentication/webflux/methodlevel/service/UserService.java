package com.example.authentication.webflux.methodlevel.service;

import com.example.authentication.webflux.methodlevel.security.UserPrincipal;
import com.example.authentication.webflux.methodlevel.domain.User;
import com.example.authentication.webflux.methodlevel.repostory.UserRepository;
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
public class UserService implements ReactiveUserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
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
}
