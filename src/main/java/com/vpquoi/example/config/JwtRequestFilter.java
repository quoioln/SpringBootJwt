package com.vpquoi.example.config;

import com.vpquoi.example.constant.Constant;
import com.vpquoi.example.model.CustomUserDetails;
import com.vpquoi.example.service.JwtUserDetailsService;
import com.vpquoi.example.service.RedisClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private RedisClientService redisClientService;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    Claims claims = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
      jwtToken = requestTokenHeader.replace("Bearer ", "");
      if (redisClientService.sismember(Constant.REDIS_SET_ACTIVE_SUBJECTS, jwtToken)) {
        try {
          claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
          logger.warn("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
          logger.warn("JWT Token has expired");
        }
      } else {
        logger.warn("JWT Token has been deleted.");
      }
    }

    // Once we get the token validate it.
    if (claims != null && claims.getSubject() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      CustomUserDetails userDetails = (CustomUserDetails) this.jwtUserDetailsService.loadUserByUsername(claims.getSubject());
      // Validate password has been changed by other token.
      if (userDetails.getLastUpdatePassword() == null || !userDetails.getLastUpdatePassword().after(claims.getIssuedAt())) {
        // If token is valid configure Spring Security to manually set authentication
        if (this.jwtTokenUtil.validateToken(jwtToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication =
                  new UsernamePasswordAuthenticationToken(
                          userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // After setting the Authentication in the context, we specify
          // that the current user is authenticated. So it passes the
          // Spring Security Configurations successfully.
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }

    }
    filterChain.doFilter(request, response);
  }
}
