package com.app.modules.users.dto;

import com.app.modules.users.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
  private String token;
  private User user;
}
