package com.example.demo.service;

import com.example.demo.domain.Meal;
import com.example.demo.domain.MealCriteria;
import com.example.demo.domain.User;

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

  /**
   * Find meals matching the search criteria
   *
   * @param searchCriteria the search criteria
   * @return the list of meals matching the search criteria
   */
  List<Meal> findByCriteria(MealCriteria searchCriteria);

  /**
   * Find all meals of the specified user and sort them by date
   *
   * @param user the user to look for
   * @return the sorted list of meals
   */
  List<Meal> findByUser(User user);

  /**
   * Delete all meal entries of the specified user
   *
   * @param user the user to delete meals for
   */
  void deleteAllByUser(User user);
}
