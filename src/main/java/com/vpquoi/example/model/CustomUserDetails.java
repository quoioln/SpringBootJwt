package com.vpquoi.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends User {

  private long id;

  private String email;


  public CustomUserDetails(
      String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public CustomUserDetails(
      long id,
      String username,
      String password,
      String email,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.email = email;
    this.id = id;
  }
}
