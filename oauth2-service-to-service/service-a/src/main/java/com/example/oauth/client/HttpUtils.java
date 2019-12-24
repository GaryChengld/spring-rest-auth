package com.example.oauth.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

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

    public static ApiResponse toApiResponse(Exception e) {
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException httpException = (HttpClientErrorException) e;
            try {
                return mapper.readValue(httpException.getResponseBodyAsString(), ApiResponse.class);
            } catch (JsonProcessingException e1) {
                log.debug(e1.getMessage());
            }
        }
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
    }
}
