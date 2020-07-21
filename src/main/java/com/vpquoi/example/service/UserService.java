package com.vpquoi.example.service;

import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.model.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> validateSignUp(SignupRequest signupRequest);

    UserDto createUser(SignupRequest signupRequest);

}
