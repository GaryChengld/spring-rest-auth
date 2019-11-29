package com.example.authentication.webflux.dao.repostory;

import com.example.authentication.webflux.dao.domain.Role;
import com.example.authentication.webflux.dao.domain.User;
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
