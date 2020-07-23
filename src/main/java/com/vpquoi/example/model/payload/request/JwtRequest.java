package com.vpquoi.example.model.payload.request;

import lombok.Data;

@Data
public class JwtRequest {

  private String username;

  private String password;
}
