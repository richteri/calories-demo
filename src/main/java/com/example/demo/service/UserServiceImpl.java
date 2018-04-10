package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.exception.ResourceConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findById(Long id) throws ResourceNotFoundException {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new ResourceNotFoundException("User was not found");
    }
    return user.get();
  }

  @Override
  public User save(User user) throws ResourceConflictException {
    if (StringUtils.hasText(user.getPassword())) {
      user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
    } else {
      User persisted = findById(user.getId());
      user.setPasswordHash(persisted.getPasswordHash());
    }
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new ResourceConflictException("Username is already in use");
    }
  }

  @Override
  public void delete(User user) {
    userRepository.delete(user);
  }
}
