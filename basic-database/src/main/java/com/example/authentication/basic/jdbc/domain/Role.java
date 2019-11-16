package com.example.authentication.basic.jdbc.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Gary Cheng
 */
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "ROLE", nullable = false, length = 100)
    private String roleName;
}
