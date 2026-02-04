package com.example.tripplanner.repository;

import com.example.tripplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
//    Optional<User> findByUsername(String username);

    Optional<User> findByResetToken(String resetToken);

    boolean existsByEmail(String email);
}
