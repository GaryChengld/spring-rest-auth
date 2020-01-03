package com.example.oauth2.serviceb.security;

import com.example.oauth2.serviceb.ApiResponse;
import com.example.oauth2.serviceb.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gary Cheng
 */
@Component("restAuthenticationEntryPoint")
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authException) throws IOException {
        log.debug("RestAuthenticationEntryPoint: {}", authException.getClass().getName());
        log.debug(httpServletRequest.getRequestURI());
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_UNAUTHORIZED, this.getExceptionMessage(authException));
        HttpUtils.writeJsonResponse(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, apiResponse);
    }

    private String getExceptionMessage(AuthenticationException authException) {
        Throwable cause = authException.getCause();
        if (null != cause) {
            log.debug("cause: {}", cause.getClass().getName());
            if (cause instanceof InvalidTokenException) {
                return "Invalid token: " + authException.getMessage();
            }
        }
        return authException.getMessage();
    }
}
