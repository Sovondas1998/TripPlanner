package com.example.tripplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO used as login response
 * Contains JWT token returned to client
 */
@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token; // JWT token string
    private String role;
}
