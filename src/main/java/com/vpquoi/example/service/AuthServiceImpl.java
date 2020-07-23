package com.vpquoi.example.service;

import com.vpquoi.example.config.JwtTokenUtil;
import com.vpquoi.example.constant.Constant;
import io.jsonwebtoken.Claims;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

  private final Log logger = LogFactory.getLog(getClass());

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtUserDetailsService userDetailsService;

  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Autowired private RedisClientService redisClientService;

  @Override
  public void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  @Override
  public Claims getClaimsFromToken(String requestTokenHeader) throws Exception {
    if (StringUtils.isEmpty(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
      logger.warn("Token is invalid");
      throw new BadCredentialsException("Token is invalid");
    }
    String jwtToken = requestTokenHeader.replace("Bearer ", "");

    Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
    return claims;
  }

  @Override
  public String generateToken(String username) {
    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    final String token = jwtTokenUtil.generateToken(userDetails);
    redisClientService.sadd(Constant.REDIS_SET_ACTIVE_SUBJECTS, token);
    return token;
  }

  @Override
  public void deleteToken(String requestTokenHeader) throws Exception {
    String jwtToken = requestTokenHeader.replace("Bearer ", "");
    boolean result = redisClientService.srem(Constant.REDIS_SET_ACTIVE_SUBJECTS, jwtToken);
    if (!result) {
      throw new Exception("Failed to delete token.");
    }
  }
}
