package com.app.modules.users.dto;

import com.app.modules.jwt.classes.JwtPayload;
import com.app.modules.users.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseDto {
  private JwtPayload jwtPayload;
  private User user;
}
