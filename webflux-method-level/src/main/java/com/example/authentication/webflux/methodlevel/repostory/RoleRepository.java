package com.example.authentication.webflux.methodlevel.repostory;

import com.example.authentication.webflux.methodlevel.domain.Role;
import com.example.authentication.webflux.methodlevel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gary Cheng
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find Role by roleName
     *
     * @param roleName
     * @return
     */
    User findByRoleName(String roleName);
}
