package com.example.tripplanner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email            // must be valid email format
    @NotBlank         // cannot be null or empty
    private String email;

    @NotBlank
    private String password;
}
