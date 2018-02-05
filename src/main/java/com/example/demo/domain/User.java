package com.example.demo.domain;

import com.example.demo.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * User Model
 */
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Size(max = 100)
  @Column(length = 100)
  private String name;

  @NotNull
  @Pattern(regexp = "^[a-zA-Z0-9._-]*$")
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  private String username;

  /**
   * Password cannot be updated after creation.
   * Hash won't be sent to client.
   */
  @Column(length = 60, nullable = false, updatable = false)
  private String password;

  @Min(0)
  @Max(10000)
  private Integer calories;

  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @JsonIgnore
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Meal> meals;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getCalories() {
    return calories;
  }

  public void setCalories(Integer calories) {
    this.calories = calories;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<Meal> getMeals() {
    return meals;
  }

  public void setMeals(List<Meal> meals) {
    this.meals = meals;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", calories=" + calories +
        ", role=" + role +
        '}';
  }
}
