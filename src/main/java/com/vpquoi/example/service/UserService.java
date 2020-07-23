package com.vpquoi.example.service;

import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.entity.User;
import com.vpquoi.example.model.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
  ResponseEntity<?> validateSignUp(SignupRequest signupRequest);

  UserDto createUser(SignupRequest signupRequest);

  ResponseEntity<?> changePassword(long userId, String password);

  ResponseEntity<?> validateEditablePermission(User user);
}
