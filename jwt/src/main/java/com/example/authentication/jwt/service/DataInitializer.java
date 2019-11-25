package com.example.authentication.jwt.service;

import com.example.authentication.jwt.domain.Role;
import com.example.authentication.jwt.domain.User;
import com.example.authentication.jwt.repostory.RoleRepository;
import com.example.authentication.jwt.repostory.UserRepository;
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
        user.setActivated(true);
        user.setUsername("user");
        user.setFirstname("User");
        user.setLastname("User");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
        user.getRoles().add(roleUser);
        userRepository.save(user);

        User disabled = new User();
        disabled.setActivated(false);
        disabled.setUsername("disabled");
        disabled.setFirstname("User");
        disabled.setLastname("User");
        disabled.setPassword(passwordEncoder.encode("mySecret"));
        userRepository.save(disabled);
        disabled.getRoles().add(roleUser);
        userRepository.save(disabled);

        User admin = new User();
        admin.setActivated(true);
        admin.setUsername("admin");
        admin.setFirstname("Admin");
        admin.setLastname("Admin");
        admin.setPassword(passwordEncoder.encode("password"));
        userRepository.save(admin);
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        userRepository.save(admin);
    }
}
