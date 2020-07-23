package com.vpquoi.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpquoi.example.model.RoleName;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {

  private long id;

  private String username;

  @JsonIgnore
  private String password;

  private String email;

  private Set<RoleName> roles;

  private Date lastUpdated;

  private Date create;
}
