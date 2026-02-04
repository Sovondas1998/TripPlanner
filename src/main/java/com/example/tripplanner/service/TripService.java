package com.example.tripplanner.service;

import com.example.tripplanner.dto.CreateTripRequest;
import com.example.tripplanner.dto.TripResponse;
import com.example.tripplanner.dto.UpdateTripRequest;
import com.example.tripplanner.model.User;

import java.util.List;
import java.util.UUID;

public interface TripService {

    /**
     * Creates a new trip for the given user.
     *
     * @param request trip creation data from client
     * @param user    authenticated user (owner of the trip)
     * @return created trip details
     */
    TripResponse createTrip(CreateTripRequest request, User user);

    List<TripResponse> getTripForUser(User user);

    /**
     * Fetch a single trip owned by the given user.
     */
    TripResponse getTripById(UUID tripId, User user);

    TripResponse updateTrip(UUID tripId, UpdateTripRequest tripRequest, User user);

    void deleteTrip(UUID tripId, User user);
}
