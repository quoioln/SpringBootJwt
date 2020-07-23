package com.vpquoi.example.entity;

import com.vpquoi.example.model.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Enumerated(EnumType.STRING)
  @Column
  private RoleName name;

  @Column private String description;
}
