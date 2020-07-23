package com.vpquoi.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class CustomUserDetails extends User {

  private long id;

  private String email;

  private Date lastUpdatePassword;

  public CustomUserDetails(
      String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public CustomUserDetails(
      long id,
      String username,
      String password,
      String email,
      Date lastUpdatePassword,
              Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.email = email;
    this.id = id;
    this.lastUpdatePassword = lastUpdatePassword;
  }
}
