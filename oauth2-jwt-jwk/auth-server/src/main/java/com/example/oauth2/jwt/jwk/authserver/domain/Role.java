package com.example.oauth2.jwt.jwk.authserver.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Gary Cheng
 */
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ROLE", nullable = false, length = 100)
    private String roleName;
}
