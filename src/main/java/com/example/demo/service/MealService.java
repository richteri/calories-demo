package com.example.demo.service;

import com.example.demo.domain.Meal;

import java.util.List;

/**
 * Meal Service
 */
public interface MealService {

  /**
   * Find all meal entries
   *
   * @return list
   */
  List<Meal> findAll();

  /**
   * Find a single meal entry
   *
   * @param id meal identifier
   * @return meal
   */
  Meal findOne(Long id);

  /**
   * Create or save a meal entry
   *
   * @param meal the entry
   * @return the updated entry
   */
  Meal save(Meal meal);

  /**
   * Delete a single meal entry
   *
   * @param meal the meal entry
   */
  void delete(Meal meal);
}
