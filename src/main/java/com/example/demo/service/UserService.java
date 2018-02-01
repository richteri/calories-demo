package com.example.demo.service;

import com.example.demo.domain.User;

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
   */
  User findOne(Long id);

  /**
   * Create or update a user entry
   *
   * @param user the entry
   * @return the updated entry
   */
  User save(User user);

  /**
   * Delete a single user entry
   *
   * @param user the entry
   */
  void delete(User user);
}
