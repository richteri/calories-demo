package com.example.demo.controller;

import com.example.demo.domain.Meal;
import com.example.demo.domain.MealCriteria;
import com.example.demo.domain.User;
import com.example.demo.service.MealService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.demo.config.SecurityConfig.currentUser;

/**
 * User API Endpoint
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserService userService;
  private MealService mealService;

  public UserController(UserService userService, MealService mealService) {
    this.userService = userService;
    this.mealService = mealService;
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/current-user")
  public ResponseEntity<User> findCurrentUser() {
    return ResponseEntity.ok(currentUser());
  }

  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @GetMapping("/{id}")
  public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
    User user = userService.findOne(id);
    return ResponseEntity.ok(user);
  }

  @PreAuthorize("hasRole('MANAGER')")
  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User saved = userService.save(user);
    return ResponseEntity.created(URI.create(saved.getId().toString())).body(saved);
  }

  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @Valid @RequestBody User user) {
    user.setId(id);
    User saved = userService.save(user);
    return ResponseEntity.ok(saved);
  }

  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    User user = userService.findOne(id);
    userService.delete(user);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @GetMapping("/{id}/meals")
  public ResponseEntity<List<Meal>> findAllMeals(@PathVariable("id") Long id) {
    User user = userService.findOne(id);
    return ResponseEntity.ok(mealService.findByUser(user));
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @PostMapping("/{id}/meals")
  public ResponseEntity<Meal> createMeal(@PathVariable("id") Long id, @Valid @RequestBody Meal meal) {
    User user = userService.findOne(id);
    meal.setUser(user);
    Meal saved = mealService.save(meal);
    return ResponseEntity.created(URI.create(saved.getId().toString())).body(saved);
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @PutMapping("/{id}/meals/{mealId}")
  public ResponseEntity<Meal> updateMeal(@PathVariable("id") Long id, @PathVariable("mealId") Long mealId,
                                         HttpServletRequest request,
                                         @Valid @RequestBody Meal meal) {
    Meal persisted = mealService.findOne(mealId);
    if (persisted.getUser().equals(currentUser()) || request.isUserInRole("ADMIN")) {
      User user = userService.findOne(id);
      meal.setUser(user);
      Meal updated = mealService.save(meal);
      return ResponseEntity.ok(updated);
    } else {
      throw new AccessDeniedException("User is not authorized to update meal [" + mealId + "]");
    }

  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @PostMapping("/{id}/meals/search")
  public ResponseEntity<List<Meal>> findMealsByDateAndTime(@PathVariable("id") Long id,
                                                           @Valid @RequestBody MealCriteria mealCriteria) {
    User user = userService.findOne(id);
    mealCriteria.setUser(user);
    return ResponseEntity.ok(mealService.findByCriteria(mealCriteria));
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @DeleteMapping("/{id}/meals/{mealId}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id, @PathVariable("mealId") Long mealId,
                                     HttpServletRequest request) {
    Meal meal = mealService.findOne(mealId);
    if (meal.getUser().equals(currentUser()) || request.isUserInRole("ADMIN")) {
      mealService.delete(meal);
    } else {
      throw new AccessDeniedException("User is not authorized to delete meal [" + mealId + "]");
    }
    return ResponseEntity.noContent().build();
  }
}
