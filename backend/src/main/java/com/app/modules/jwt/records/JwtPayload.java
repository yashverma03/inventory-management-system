package com.app.modules.jwt.records;

import com.app.modules.users.enums.UserRoleEnum;

public record JwtPayload(
    Long userId,
    UserRoleEnum role) {
}
