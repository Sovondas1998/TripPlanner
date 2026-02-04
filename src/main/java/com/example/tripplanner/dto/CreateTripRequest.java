package com.example.tripplanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTripRequest {
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
}
