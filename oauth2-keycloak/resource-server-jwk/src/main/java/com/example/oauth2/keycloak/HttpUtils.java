package com.example.oauth2.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Gary Cheng
 */
@Slf4j
public class HttpUtils {
    private HttpUtils() {
    }

    private static ObjectMapper mapper = new ObjectMapper();

    public static void writeJsonResponse(HttpServletResponse response, int httpStatus, Object body) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream out = response.getOutputStream();
        mapper.writeValue(out, body);
        out.flush();
    }
}
