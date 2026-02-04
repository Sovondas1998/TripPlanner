package com.example.tripplanner.config;

import com.example.tripplanner.model.TestEntity;
import com.example.tripplanner.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TestRepository testRepository;

    @Override
    public void run(String... args) {
        TestEntity entity = new TestEntity();
        entity.setName("DB Working!");
        testRepository.save(entity);
    }
}
