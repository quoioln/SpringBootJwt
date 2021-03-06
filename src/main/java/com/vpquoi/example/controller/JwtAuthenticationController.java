package com.vpquoi.example.controller;

import com.vpquoi.example.config.JwtTokenUtil;
import com.vpquoi.example.constant.Constant;
import com.vpquoi.example.model.payload.request.JwtRequest;
import com.vpquoi.example.model.payload.response.JwtResponse;
import com.vpquoi.example.service.AuthService;
import com.vpquoi.example.service.JwtUserDetailsService;
import com.vpquoi.example.service.RedisClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth/token")
public class JwtAuthenticationController {

  @Autowired private AuthService authService;

  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Autowired private JwtUserDetailsService userDetailsService;

  @Autowired private RedisClientService redisClientService;

  private final Log logger = LogFactory.getLog(getClass());

  @PostMapping
  public ResponseEntity<?> authenticate(@RequestBody JwtRequest authenticationRequest)
      throws Exception {
    authService.authenticate(
        authenticationRequest.getUsername(), authenticationRequest.getPassword());
    String token = authService.generateToken(authenticationRequest.getUsername());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @GetMapping
  public ResponseEntity<?> refreshToken(
      @RequestHeader("Authorization") final String requestTokenHeader) throws Exception {
    String username = null;
    String jwtToken = requestTokenHeader.replace("Bearer ", "");
    boolean existedToken =
        redisClientService.sismember(Constant.REDIS_SET_ACTIVE_SUBJECTS, jwtToken);
    if (!existedToken) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    try {
      Claims claims = authService.getClaimsFromToken(requestTokenHeader);
      username = claims.getSubject();
    } catch (ExpiredJwtException e) {
      Claims expiredClaims = e.getClaims();
      long issueJwtDate = expiredClaims.getExpiration().getTime();
      long expriedJwt = issueJwtDate + Constant.JWT_REFRESH_TOKEN_VALIDITY * 1000;
      long now = System.currentTimeMillis();
      if (now > expriedJwt) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has been expired.");
      }
      username = expiredClaims.getSubject();
    }
    authService.deleteToken(requestTokenHeader);
    String token = authService.generateToken(username);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteToken(
      @RequestHeader("Authorization") final String requestTokenHeader) throws Exception {
    authService.deleteToken(requestTokenHeader);
    return ResponseEntity.ok().build();
  }
}
