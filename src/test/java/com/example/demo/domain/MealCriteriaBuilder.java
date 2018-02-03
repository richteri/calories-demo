package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.demo.domain.MealCriteria.DATE_MAX;
import static com.example.demo.domain.MealCriteria.DATE_MIN;
import static com.example.demo.domain.MealCriteria.TIME_MAX;
import static com.example.demo.domain.MealCriteria.TIME_MIN;

public final class MealCriteriaBuilder {
  private User user;
  private LocalDate startDate = DATE_MIN;
  private LocalDate endDate = DATE_MAX;
  private LocalTime startTime = TIME_MIN;
  private LocalTime endTime = TIME_MAX;

  private MealCriteriaBuilder() {
  }

  public static MealCriteriaBuilder aMealCriteria() {
    return new MealCriteriaBuilder();
  }

  public MealCriteriaBuilder withUser(User user) {
    this.user = user;
    return this;
  }

  public MealCriteriaBuilder withStartDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  public MealCriteriaBuilder withEndDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  public MealCriteriaBuilder withStartTime(LocalTime startTime) {
    this.startTime = startTime;
    return this;
  }

  public MealCriteriaBuilder withEndTime(LocalTime endTime) {
    this.endTime = endTime;
    return this;
  }

  public MealCriteria build() {
    MealCriteria mealCriteria = new MealCriteria();
    mealCriteria.setUser(user);
    mealCriteria.setStartDate(startDate);
    mealCriteria.setEndDate(endDate);
    mealCriteria.setStartTime(startTime);
    mealCriteria.setEndTime(endTime);
    return mealCriteria;
  }
}
