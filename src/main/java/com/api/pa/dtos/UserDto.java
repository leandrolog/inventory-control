package com.api.pa.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank
    private String userName;
    @NotBlank
    private String role;
    @NotBlank
    private String requests;

}
