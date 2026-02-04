package com.example.tripplanner.repository;

import com.example.tripplanner.model.Trip;
import com.example.tripplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    //fetch all trip for a specific user
    List<Trip> findByOwner(User owner);

    /**
     * Fetch a trip only if it belongs to the given user.
     * Prevents ID-guessing attacks.
     */
    Optional<Trip> findByIdAndOwner(UUID id, User owner);
}
