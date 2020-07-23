package com.vpquoi.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2/test")
public class TestController2 {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  //    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  //    @PreAuthorize("hasRole('MODERATOR')")
  @Secured({"ROLE_MODERATOR"})
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  //    @PreAuthorize("hasRole('ADMIN')")
  @Secured({"ROLE_ADMIN"})
  public String adminAccess() {
    return "Admin Board.";
  }
}
