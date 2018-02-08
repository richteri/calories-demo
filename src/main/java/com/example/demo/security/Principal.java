package com.example.demo.security;

import com.example.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Spring-specific user wrapper
 */
public class Principal implements UserDetails {

  private final User user;
  private final Set<GrantedAuthority> authorities;

  Principal(User user) {
    this.user = user;
    if (user.getRole() == null) {
      authorities = Collections.emptySet();
    } else {
      authorities = user.getRole().getAuthorities();
    }
  }

  public Long getId() {
    return user.getId();
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPasswordHash();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
