package com.vpquoi.example.controller;

import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.model.SignupRequest;
import com.vpquoi.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) throws Exception {
        ResponseEntity<?> validatedReq = userService.validateSignUp(signupRequest);
        if (!validatedReq.getStatusCode().equals(HttpStatus.OK)) {
            return validatedReq;
        }
        UserDto userDto = userService.createUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

}

