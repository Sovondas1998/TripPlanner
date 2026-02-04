package com.example.tripplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "trips")
@Getter
@Setter
public class Trip extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String destination; //Destination of the trip

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User owner;


}
