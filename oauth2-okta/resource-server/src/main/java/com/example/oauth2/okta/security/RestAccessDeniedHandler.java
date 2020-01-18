package com.example.oauth2.okta.security;

import com.example.oauth2.okta.ApiResponse;
import com.example.oauth2.okta.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gary Cheng
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.debug("Invoked RestAccessDeniedHandler");
        ApiResponse apiResponse = new ApiResponse(HttpServletResponse.SC_FORBIDDEN, "Access is denied");
        HttpUtils.writeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, apiResponse);
    }
}
