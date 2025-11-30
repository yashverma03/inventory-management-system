package com.app.modules.users.enums;

public enum UserRoleEnum {
  ADMIN("admin"),
  MANAGER("manager");

  private final String value;

  UserRoleEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
