package com.api.pa.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank
    private String role;
    private String department;
    private String requests;
    private String email;
    private String name;
    private String password;

}
