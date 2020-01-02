package com.example.oauth.authserver.repostory;

import com.example.oauth.authserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gary Cheng
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
