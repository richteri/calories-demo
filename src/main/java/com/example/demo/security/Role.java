package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Role Hierarchy
 */
public enum Role {
  USER(),
  MANAGER(USER),
  ADMIN(USER, MANAGER);

  private Set<GrantedAuthority> authorities;

  /**
   * Set up role hierarchy
   *
   * @param prerequisites roles granted implicitly
   */
  Role(Role... prerequisites) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
    for (Role role : prerequisites) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    this.authorities = Collections.unmodifiableSet(authorities);
  }

  /**
   * Return all granted authorities
   *
   * @return granted authorities
   */
  public Set<GrantedAuthority> getAuthorities() {
    return authorities;
  }

}
