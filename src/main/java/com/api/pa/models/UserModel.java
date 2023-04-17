package com.api.pa.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserModel {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String requests; // relacionar com requests

}
