package com.example.tripplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private UUID id;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
