package com.example.authentication.methodlevel.service;

import com.example.authentication.methodlevel.domain.Role;
import com.example.authentication.methodlevel.domain.User;
import com.example.authentication.methodlevel.repostory.RoleRepository;
import com.example.authentication.methodlevel.repostory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Gary Cheng
 */
@Slf4j
@Service
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.debug("on applicationReadyEvent");
        Role roleUser = new Role();
        roleUser.setRoleName("ROLE_USER");
        roleUser = roleRepository.save(roleUser);
        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ROLE_ADMIN");
        roleAdmin = roleRepository.save(roleAdmin);

        User user = new User();
        user.setActive(1);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
        User admin = new User();
        admin.setActive(1);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        userRepository.save(admin);
        user.getRoles().add(roleUser);
        userRepository.save(user);
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        userRepository.save(admin);
    }
}
