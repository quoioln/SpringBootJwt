package com.vpquoi.example.model.payload.request;

import com.vpquoi.example.model.RoleName;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {

  private String username;

  private String email;

  private String password;

  private Set<RoleName> roles;
}
