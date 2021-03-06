package com.example.oauth2.okta;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * @author Gary Cheng
 */
@Component
@Slf4j
public class QueryResolver implements GraphQLQueryResolver {
    public ApiResponse publicApi() {
        return new ApiResponse("Public Api");
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Everyone')")
    public ApiResponse userApi() {
        return new ApiResponse("User Api");
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    public ApiResponse adminApi() {
        return new ApiResponse("Admin Api");
    }
}
