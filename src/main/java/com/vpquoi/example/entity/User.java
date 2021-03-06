package com.vpquoi.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpquoi.example.dto.UserDto;
import com.vpquoi.example.model.CustomUserDetails;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@Entity
public class User extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column @JsonIgnore private String password;

  @Column(unique = true)
  private String email;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "USER_ROLES",
          joinColumns = {@JoinColumn(name = "USER_ID")},
          inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
  private Set<Role> roles;

  @Column(name = "last_update_password")
  private Date lastUpdatePassword;

  public User() {
  }

  public UserDetails toUserDetails() {
    List<GrantedAuthority> authorities =
            this.roles.stream()
                    .map(e -> new SimpleGrantedAuthority(e.getName().name()))
                    .collect(Collectors.toList());
    return new CustomUserDetails(this.id, this.username, this.password, this.email, this.lastUpdatePassword, authorities);
  }

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public UserDto toDto() {
    UserDto dto = new UserDto();
    copyProperties(this, dto, "roles", "password");
    dto.setRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));
    return dto;
  }
}
