package com.example.demo.repository;

import com.example.demo.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Meal Repository
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}
