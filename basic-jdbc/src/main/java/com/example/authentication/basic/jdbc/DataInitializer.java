package com.example.authentication.basic.jdbc;

import com.example.authentication.basic.jdbc.domain.Role;
import com.example.authentication.basic.jdbc.domain.User;
import com.example.authentication.basic.jdbc.repostory.RoleRepository;
import com.example.authentication.basic.jdbc.repostory.UserRepository;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
