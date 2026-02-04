package com.example.tripplanner.service.impl;

import com.example.tripplanner.dto.CreateTripRequest;
import com.example.tripplanner.dto.TripResponse;
import com.example.tripplanner.dto.UpdateTripRequest;
import com.example.tripplanner.exception.BadRequestException;
import com.example.tripplanner.exception.ResourceNotFoundException;
import com.example.tripplanner.model.Trip;
import com.example.tripplanner.model.User;
import com.example.tripplanner.repository.TripRepository;
import com.example.tripplanner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;


    @Override
    public TripResponse getTripById(UUID tripId, User user){
        //Fetch trip only if owned by user
        Trip trip = tripRepository.findByIdAndOwner(tripId,user)
                .orElseThrow(()->
                        new ResourceNotFoundException("Trip not found")
                );
        //Map entity -> dto
        return mapToResponse(trip);
    }

    @Override
    public TripResponse updateTrip(UUID tripId, UpdateTripRequest tripRequest, User user) {

        //Ownership validation
        Trip trip = tripRepository.findByIdAndOwner(tripId,user)
                .orElseThrow(()->
                        new ResourceNotFoundException("Trip not found")
                );
        //Update only NULL fields
        if (tripRequest.getTitle()!= null){
            trip.setTitle(tripRequest.getTitle());
        }

        if (tripRequest.getDestination()!= null){
            trip.setDestination(tripRequest.getDestination());
        }

        if (tripRequest.getStartDate()!=null){
            trip.setStartDate(tripRequest.getStartDate());
        }
        if (tripRequest.getEndDate()!=null){
            trip.setEndDate(tripRequest.getEndDate());
        }

        //Business Validation
        if (tripRequest.getStartDate().isAfter(tripRequest.getEndDate())){
            throw new BadRequestException("End Date cannot be before Start Date");
        }

        Trip saved = tripRepository.save(trip);
        return mapToResponse(saved);

    }

    @Override
    public void deleteTrip(UUID tripId, User user) {
        Trip trip = tripRepository.findByIdAndOwner(tripId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found")
                );

        tripRepository.delete(trip);
    }

    @Override
    public List<TripResponse> getTripForUser(User user) {
        //1 Fetch Trip owned by the authenticated user
        List<Trip> trips = tripRepository.findByOwner(user);


        //2 Convert entities → DTOs
        return trips.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TripResponse createTrip(CreateTripRequest request, User user) {

        // 1️⃣ Validate input dates
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        // 2️⃣ Create Trip entity
        Trip trip = new Trip();
        trip.setTitle(request.getTitle());
        trip.setDestination(request.getDestination());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());

        // 3️⃣ Assign authenticated user as owner
        trip.setOwner(user);

        // 4️⃣ Persist trip
        Trip savedTrip = tripRepository.save(trip);

        // 5️⃣ Map entity → response DTO
        return mapToResponse(savedTrip);
    }

    /**
     * Maps Trip entity to TripResponse DTO
     */
    private TripResponse mapToResponse(Trip trip) {
        TripResponse response = new TripResponse();
        response.setId(trip.getId());
        response.setTitle(trip.getTitle());
        response.setDestination(trip.getDestination());
        response.setStartDate(trip.getStartDate());
        response.setEndDate(trip.getEndDate());
        return response;
    }
}
