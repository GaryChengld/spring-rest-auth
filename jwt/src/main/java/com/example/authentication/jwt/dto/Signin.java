package com.example.authentication.jwt.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Gary Cheng
 */
@Data
public class Signin {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
