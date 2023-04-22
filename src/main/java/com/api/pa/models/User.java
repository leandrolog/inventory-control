package com.api.pa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Integer userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Request> request;

}
