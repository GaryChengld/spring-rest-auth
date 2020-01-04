package com.example.oauth2.jwt.jws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gary Cheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Integer status;
    private String message;

    public ApiResponse(String message) {
        this.status = 200;
        this.message = message;
    }
}
