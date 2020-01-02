package com.example.graphql;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gary Cheng
 */
@Configuration
public class GraphQLConfiguration {

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                return errors.stream()
                        .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError(e) : e)
                        .collect(Collectors.toList());
            }
        };
    }
}
