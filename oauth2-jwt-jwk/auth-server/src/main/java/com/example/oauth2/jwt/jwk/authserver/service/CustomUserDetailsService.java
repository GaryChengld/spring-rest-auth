package com.example.oauth2.jwt.jwk.authserver.service;

import com.example.oauth2.jwt.jwk.authserver.UserPrincipal;
import com.example.oauth2.jwt.jwk.authserver.domain.User;
import com.example.oauth2.jwt.jwk.authserver.repostory.UserRepository;
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
}
