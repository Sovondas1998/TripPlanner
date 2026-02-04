package com.example.tripplanner.controller;

import com.example.tripplanner.dto.CreateTripRequest;
import com.example.tripplanner.dto.TripResponse;
import com.example.tripplanner.model.User;
import com.example.tripplanner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    // ✅ CREATE TRIP
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public TripResponse createTrip(
            @RequestBody CreateTripRequest request,
            @AuthenticationPrincipal User user // 🔥 injected automatically
    ) {
        System.out.println("🔥 Controller reached");
        return tripService.createTrip(request, user);
    }


    //Get all trip for logged-in user
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<TripResponse> getMyTrips(
            @AuthenticationPrincipal User user
    ){
        return tripService.getTripForUser(user);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public TripResponse getMyTripByID(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
            ){
        return tripService.getTripById(id,user);
    }
}
