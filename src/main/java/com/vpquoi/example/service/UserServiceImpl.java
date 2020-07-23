package com.vpquoi.example.service;

import com.vpquoi.example.constant.Constant;
import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.entity.Role;
import com.vpquoi.example.entity.User;
import com.vpquoi.example.model.CustomUserDetails;
import com.vpquoi.example.model.RoleName;
import com.vpquoi.example.model.payload.request.SignupRequest;
import com.vpquoi.example.repository.RoleRepository;
import com.vpquoi.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserRepository userRepository;

  @Resource
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Transactional
  @Override
  public ResponseEntity<?> validateSignUp(SignupRequest signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity.badRequest()
              .body(com.vpquoi.example.model.payload.response.MessageResponse.builder().error(Constant.MessageResponse.TOKEN_USERNAME).status(400).build());
    }

    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.badRequest()
              .body(com.vpquoi.example.model.payload.response.MessageResponse.builder().error(Constant.MessageResponse.TOKEN_EMAIL).status(400).build());
    }
    return ResponseEntity.ok().build();
  }

  @Override
  public UserDto createUser(SignupRequest signUpRequest) {
    // Create new user's account
    User user =
            new User(
                    signUpRequest.getUsername(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getEmail());

    Set<RoleName> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole =
              roleRepository
                      .findByName(RoleName.ROLE_USER)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
              e -> {
                Role role =
                        roleRepository
                                .findByName(e)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
              });
    }

    user.setRoles(roles);
    User createdUser = userRepository.save(user);

    return createdUser.toDto();
  }

  @Override
  public ResponseEntity<?> changePassword(long userId, String password) {
    User user = this.userRepository.findById(userId).get();
    ResponseEntity<?> validatedRs = this.validateEditablePermission(user);
    if (!HttpStatus.OK.equals(validatedRs.getStatusCode())) {
      return validatedRs;
    }
    user.setPassword(encoder.encode(password));
    user.setLastUpdatePassword(new Date());
    this.userRepository.save(user);
    return ResponseEntity.ok(
            com.vpquoi.example.model.payload.response.MessageResponse.builder()
                    .status(200)
                    .message(Constant.MessageResponse.CHANGE_PASSWORD_SUCCESSFUL)
                    .build());
  }

  @Override
  public ResponseEntity<?> validateEditablePermission(User user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // Validate role.
    if (!user.getId().equals(((CustomUserDetails) authentication.getPrincipal()).getId())) {
      for (Role role : user.getRoles()) {
        if (role.getName().equals(RoleName.ROLE_MODERATOR)
                &&
                !authentication.getAuthorities()
                        .stream()
                        .anyMatch(e -> RoleName.valueOf(e.getAuthority()).equals(RoleName.ROLE_ADMIN)
                                ||
                                RoleName.valueOf(e.getAuthority()).equals(RoleName.ROLE_SUPER_ADMIN))) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                  com.vpquoi.example.model.payload.response.MessageResponse.builder()
                          .error(Constant.MessageResponse.ACTION_DENIED)
                          .build());

        }
        if (role.getName().equals(RoleName.ROLE_ADMIN)
                &&
                !authentication.getAuthorities()
                        .stream()
                        .anyMatch(e -> RoleName.valueOf(e.getAuthority()).equals(RoleName.ROLE_SUPER_ADMIN))) {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                  com.vpquoi.example.model.payload.response.MessageResponse.builder()
                          .error(Constant.MessageResponse.ACTION_DENIED)
                          .build());

        }
        // Need to validate account locked or disabled :)))
      }
    }
    return ResponseEntity.ok().build();
  }
}
