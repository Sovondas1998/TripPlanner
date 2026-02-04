package com.example.tripplanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateTripRequest {

    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
}
