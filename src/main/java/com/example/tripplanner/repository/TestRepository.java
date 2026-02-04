package com.example.tripplanner.repository;

import com.example.tripplanner.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRepository extends JpaRepository<TestEntity, UUID> {
}
