package com.example.tripplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "test_entity")
@Getter
@Setter
public class TestEntity extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;

    /**
     * Simple column used to verify DB connectivity
     */
    @Column(nullable = false)
    private String name;
}
