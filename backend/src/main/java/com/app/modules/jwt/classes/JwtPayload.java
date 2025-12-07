package com.app.modules.jwt.classes;

import com.app.modules.users.enums.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayload {
    Long userId;
    UserRoleEnum role;
}
