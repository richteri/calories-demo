package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {
  /**
   * Find all user entries
   *
   * @return list
   */
  List<User> findAll();

  /**
   * Find a single user by its identifier
   *
   * @param id the identifier
   * @return the user
   * @throws ResourceNotFoundException if the ID was not found
   */
  User findOne(Long id) throws ResourceNotFoundException;

  /**
   * Create or update a user entry
   *
   * @param user the entry
   * @return the updated entry
   * @throws ResourceNotFoundException if the unique username constraint is violated
   */
  User save(User user) throws ResourceConflictException;

  /**
   * Delete a single user entry
   *
   * @param user the entry
   */
  void delete(User user);
}
