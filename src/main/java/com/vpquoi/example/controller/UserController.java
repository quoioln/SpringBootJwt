package com.vpquoi.example.controller;

import com.vpquoi.example.constant.Constant;
import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.entity.User;
import com.vpquoi.example.model.payload.request.ChangePassRequest;
import com.vpquoi.example.model.payload.request.SignupRequest;
import com.vpquoi.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @RolesAllowed({"ADMIN"})
  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) throws Exception {
    ResponseEntity<?> validatedReq = userService.validateSignUp(signupRequest);
    if (!validatedReq.getStatusCode().equals(HttpStatus.OK)) {
      return validatedReq;
    }
    UserDto userDto = userService.createUser(signupRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUserProfile(@PathVariable long userId) {
    User user = null;
    UserDto userDto = user.toDto();
    return ResponseEntity.ok(userDto);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> updateProfile(@PathVariable long userId) {
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("(#userId == authentication.principal.id) or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
  @PostMapping("/{userId}/change-password")
  public ResponseEntity<?> changePassword(@PathVariable long userId, @Valid @RequestBody ChangePassRequest changePassRequest) {
    if (!changePassRequest.getNewPassword().equals(changePassRequest.getConfirmPassword())) {
      return ResponseEntity.badRequest().body(
              com.vpquoi.example.model.payload.response.MessageResponse.builder()
                      .status(400)
                      .message(Constant.MessageResponse.MISMATCH_PASSWORD)
                      .build());
    }

    return userService.changePassword(userId, changePassRequest.getNewPassword());
  }
}
