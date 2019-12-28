package com.example.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * @author Gary Cheng
 */
@Component
public class ApiQuery implements GraphQLQueryResolver {

    public ApiInfo about() {
        return new ApiInfo("public api", "v1");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ApiInfo userApi() {
        return new ApiInfo("user api", "v1");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ApiInfo adminApi() {
        return new ApiInfo("admin api", "v1");
    }
}
