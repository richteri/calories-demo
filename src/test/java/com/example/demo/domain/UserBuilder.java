package com.example.demo.domain;

import com.example.demo.security.Role;

import java.util.List;

public final class UserBuilder {
  private Long id;
  private String name;
  private String username;
  private String password;
  private Integer calories;
  private Role role = Role.USER;
  private List<Meal> meals;

  private UserBuilder() {
  }

  public static UserBuilder anUser() {
    return new UserBuilder();
  }

  public UserBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public UserBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder withCalories(Integer calories) {
    this.calories = calories;
    return this;
  }

  public UserBuilder withRole(Role role) {
    this.role = role;
    return this;
  }

  public UserBuilder withMeals(List<Meal> meals) {
    this.meals = meals;
    return this;
  }

  public User build() {
    User user = new User();
    user.setId(id);
    user.setName(name);
    user.setUsername(username);
    user.setPassword(password);
    user.setCalories(calories);
    user.setRole(role);
    user.setMeals(meals);
    return user;
  }
}
