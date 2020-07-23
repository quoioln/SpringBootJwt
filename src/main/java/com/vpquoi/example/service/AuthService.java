package com.vpquoi.example.service;

import io.jsonwebtoken.Claims;

public interface AuthService {

  void authenticate(String username, String password) throws Exception;

  Claims getClaimsFromToken(String requestTokenHeader) throws Exception;

  String generateToken(String username);

  void deleteToken(String requestTokenHeader) throws Exception;
}
