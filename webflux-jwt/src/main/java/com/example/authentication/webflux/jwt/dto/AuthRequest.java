package com.example.authentication.webflux.jwt.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Gary Cheng
 */
@Data
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
