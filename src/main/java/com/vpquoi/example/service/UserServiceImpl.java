package com.vpquoi.example.service;

import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.entity.Role;
import com.vpquoi.example.entity.User;
import com.vpquoi.example.model.MessageResponse;
import com.vpquoi.example.model.RoleName;
import com.vpquoi.example.model.SignupRequest;
import com.vpquoi.example.repository.RoleRepository;
import com.vpquoi.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public UserDto createUser(SignupRequest signUpRequest) {
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail());

        Set<RoleName> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(e -> {
                Role role = roleRepository.findByName(e)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);

            });
        }

        user.setRoles(roles);
        User createdUser = userRepository.save(user);

        return createdUser.toDto();
    }
}
