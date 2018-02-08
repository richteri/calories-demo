package com.example.demo.service;

import com.example.demo.domain.Meal;
import com.example.demo.domain.MealCriteria;
import com.example.demo.domain.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MealRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

  private MealRepository mealRepository;

  public MealServiceImpl(MealRepository mealRepository) {
    this.mealRepository = mealRepository;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Override
  public List<Meal> findAll() {
    return mealRepository.findAll();
  }

  @Override
  public Meal findOne(Long id) throws ResourceNotFoundException {
    Meal meal = mealRepository.findOne(id);
    if (meal == null) {
      throw new ResourceNotFoundException("Meal was not found");
    }
    return meal;
  }

  @Override
  public Meal save(Meal meal) {
    return mealRepository.save(meal);
  }

  @Override
  public void delete(Meal meal) {
    mealRepository.delete(meal);
  }

  @Override
  public List<Meal> findByCriteria(MealCriteria searchCriteria) {
    return mealRepository.findByUserAndDateBetweenAndTimeBetween(searchCriteria.getUser(),
        searchCriteria.getStartDate(), searchCriteria.getEndDate(),
        searchCriteria.getStartTime(), searchCriteria.getEndTime());
  }

  @Override
  public List<Meal> findByUser(User user) {
    return mealRepository.findByUserOrderByDateDescTime(user);
  }

  @Override
  public void deleteAllByUser(User user) {
    mealRepository.deleteAllByUser(user);
  }
}
