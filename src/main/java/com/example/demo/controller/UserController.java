package com.example.demo.controller;

import com.example.demo.domain.Meal;
import com.example.demo.domain.MealCriteria;
import com.example.demo.domain.User;
import com.example.demo.service.MealService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
 * User API Endpoints
 */
@Api(value = "demo", description = "User management endpoints")
@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserService userService;
  private MealService mealService;

  public UserController(UserService userService, MealService mealService) {
    this.userService = userService;
    this.mealService = mealService;
  }

  /**
   * View a list of available users
   * Only for ADMINs and MANAGERs
   *
   * @return the user list with HTTP 200
   */
  @ApiOperation(value = "View a list of available users", response = List.class)
  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<User>> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  /**
   * Find the authenticated user for the current session
   * or send HTTP 401 if the user is not authenticated
   *
   * @return the current user and HTTP 200
   */
  @ApiOperation(value = "Find the authenticated user for the current session", response = User.class)
  @PreAuthorize("isAuthenticated()")
  @GetMapping(value = "/current-user", produces = "application/json")
  public ResponseEntity<User> findCurrentUser() {
    return ResponseEntity.ok(currentUser());
  }

  /**
   * Find a single user by its ID.
   * Only for ADMINs, MANAGERs and the authenticated user can access their own records
   *
   * @param id the ID to look for
   * @return the user and HTTP 200 or HTTP 404 if not found
   */
  @ApiOperation(value = "Find a single user by its ID", response = User.class)
  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
  }

  /**
   * Create a user.
   * Only for ADMINs and MANAGERs.
   *
   * @param user the user to be created
   * @return the created user and its ID in the Location header with HTTP 201
   */
  @ApiOperation(value = "Create a user", response = User.class)
  @PreAuthorize("hasRole('MANAGER')")
  @PostMapping(produces = "application/json")
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User saved = userService.save(user);
    return ResponseEntity.created(URI.create(saved.getId().toString())).body(saved);
  }

  /**
   * Update a user.
   * Only for ADMNs, MANAGERs and the authenticated user can access their own records
   *
   * @param id the ID of the user to be updated
   * @param user the updated values
   * @return the updated user with HTTP 200
   */
  @ApiOperation(value = "Update a user", response = User.class)
  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<User> update(@PathVariable("id") Long id, @Valid @RequestBody User user) {
    // make sure the payload has the right ID to avoid updating someone else's record
    user.setId(id);
    User saved = userService.save(user);
    return ResponseEntity.ok(saved);
  }

  /**
   * Delete a user by its ID.
   * Only for ADMINs, MANAGERs and the authenticated user can access their own records
   *
   * @param id the user ID to look for
   * @return HTTP 203 if the delete was successful, HTTP 404 if the user was not found
   */
  @ApiOperation(value = "Delete a user")
  @PreAuthorize("principal.id == #id or hasRole('MANAGER')")
  @DeleteMapping(value = "/{id}", produces = "application/json")
  @Transactional
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    User user = userService.findById(id);
    mealService.deleteAllByUser(user);
    userService.delete(user);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation(value = "View all meals associated with a user", response = List.class)
  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @GetMapping(value = "/{id}/meals", produces = "application/json")
  public ResponseEntity<List<Meal>> findAllMeals(@PathVariable("id") Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(mealService.findByUser(user));
  }

  @ApiOperation(value = "Create a new meal", response = Meal.class)
  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @PostMapping(value = "/{id}/meals", produces = "application/json")
  public ResponseEntity<Meal> createMeal(@PathVariable("id") Long id, @Valid @RequestBody Meal meal) {
    User user = userService.findById(id);
    meal.setUser(user);
    Meal saved = mealService.save(meal);
    return ResponseEntity.created(URI.create(saved.getId().toString())).body(saved);
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @PutMapping("/{id}/meals/{mealId}")
  public ResponseEntity<Meal> updateMeal(@PathVariable("id") Long id, @PathVariable("mealId") Long mealId,
                                         HttpServletRequest request,
                                         @Valid @RequestBody Meal meal) {
    Meal persisted = mealService.findById(mealId);
    if (persisted.getUser().equals(currentUser()) || request.isUserInRole("ADMIN")) {
      User user = userService.findById(id);
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
    User user = userService.findById(id);
    mealCriteria.setUser(user);
    return ResponseEntity.ok(mealService.findByCriteria(mealCriteria));
  }

  @PreAuthorize("principal.id == #id or hasRole('ADMIN')")
  @DeleteMapping("/{id}/meals/{mealId}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id, @PathVariable("mealId") Long mealId,
                                     HttpServletRequest request) {
    Meal meal = mealService.findById(mealId);
    if (meal.getUser().equals(currentUser()) || request.isUserInRole("ADMIN")) {
      mealService.delete(meal);
    } else {
      throw new AccessDeniedException("User is not authorized to delete meal [" + mealId + "]");
    }
    return ResponseEntity.noContent().build();
  }
}
