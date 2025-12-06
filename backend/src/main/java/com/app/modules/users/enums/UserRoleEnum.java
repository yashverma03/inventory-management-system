package com.app.modules.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRoleEnum {
  ADMIN("ADMIN"),
  MANAGER("MANAGER");

  private final String value;

  UserRoleEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static UserRoleEnum fromValue(String value) {
    if (value == null) {
      return null;
    }
    for (UserRoleEnum role : UserRoleEnum.values()) {
      if (role.value.equalsIgnoreCase(value)) {
        return role;
      }
    }
    throw new IllegalArgumentException("Unknown role: " + value + ". Allowed values: ADMIN, MANAGER");
  }
}
