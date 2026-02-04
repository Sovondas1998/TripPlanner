package com.example.tripplanner.dto;

import lombok.Getter;

/**
 * DTO for change password request
 * This comes from the client (Postman / UI)
 */
@Getter
public class ChangePasswordRequest {

    // Old password entered by the user
    private String oldPassword;

    // New password user wants to set
    private String newPassword;
}
