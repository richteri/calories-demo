package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Search criteria for meals
 */
public class MealCriteria {
  public static final LocalDate DATE_MIN = LocalDate.parse("0000-01-01");
  public static final LocalDate DATE_MAX = LocalDate.parse("9999-12-31");
  public static final LocalTime TIME_MIN = LocalTime.parse("00:00:00");
  public static final LocalTime TIME_MAX = LocalTime.parse("23:59:59.999");

  private User user;
  private LocalDate startDate = DATE_MIN;
  private LocalDate endDate = DATE_MAX;
  private LocalTime startTime = TIME_MIN;
  private LocalTime endTime = TIME_MAX;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }
}
