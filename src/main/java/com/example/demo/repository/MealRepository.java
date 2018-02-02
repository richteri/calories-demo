package com.example.demo.repository;

import com.example.demo.domain.Meal;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Meal Repository
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
  /**
   * Find meals for the given user based on the date and time constraints
   *
   * @param user      the user to look for
   * @param startDate the start date of the meal
   * @param endDate   the end date of the meal
   * @param startTime the start time of the meal
   * @param endTime   the end time of the meal
   * @return list of meals matching the criteria
   */
  List<Meal> findByUserAndDateBetweenAndTimeBetween(User user,
                                                    LocalDate startDate, LocalDate endDate,
                                                    LocalTime startTime, LocalTime endTime);
}
