package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public final class MealBuilder {
  private Long id;
  private String description;
  private LocalDate date;
  private LocalTime time;
  private Integer calories;
  private User user;

  private MealBuilder() {
  }

  public static MealBuilder aMeal() {
    return new MealBuilder();
  }

  public MealBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public MealBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public MealBuilder withDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public MealBuilder withTime(LocalTime time) {
    this.time = time;
    return this;
  }

  public MealBuilder withCalories(Integer calories) {
    this.calories = calories;
    return this;
  }

  public MealBuilder withUser(User user) {
    this.user = user;
    return this;
  }

  public Meal build() {
    Meal meal = new Meal();
    meal.setId(id);
    meal.setDescription(description);
    meal.setDate(date);
    meal.setTime(time);
    meal.setCalories(calories);
    meal.setUser(user);
    return meal;
  }
}
