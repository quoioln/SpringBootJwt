package com.vpquoi.example.model.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePassRequest {

  @NotBlank(message = "New password is mandatory")
  private String newPassword;

  @NotBlank(message = "Confirmation new password is mandatory")
  private String confirmPassword;
}
