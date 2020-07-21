package com.vpquoi.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpquoi.example.entity.Role;
import com.vpquoi.example.entity.User;
import com.vpquoi.example.model.RoleName;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
public class UserDto {

    private long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private Set<RoleName> roles;

}
