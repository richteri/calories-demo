package com.example.demo.controller;

import com.example.demo.domain.Meal;
import com.example.demo.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Meal API Endpoints for admins only
 */
@RestController
@RequestMapping("/api/meals")
public class MealController {

  private MealService mealService;

  @Autowired
  public MealController(MealService mealService) {
    this.mealService = mealService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<List<Meal>> findAll() {
    return ResponseEntity.ok(mealService.findAll());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal) {
    Meal saved = mealService.save(meal);
    return ResponseEntity.created(URI.create(saved.getId().toString())).body(saved);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Meal> update(@PathVariable("id") Long id, @Valid @RequestBody Meal meal) {
    meal.setId(id);
    Meal saved = mealService.save(meal);
    return ResponseEntity.ok(saved);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    Meal meal = mealService.findById(id);
    mealService.delete(meal);
    return ResponseEntity.noContent().build();
  }
}
