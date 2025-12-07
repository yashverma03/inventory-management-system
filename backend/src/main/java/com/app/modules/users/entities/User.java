package com.app.modules.users.entities;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.app.common.entities.BaseEntity;
import com.app.modules.inventory.entities.Inventory;
import com.app.modules.users.enums.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = true)
  private String lastName;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "role", nullable = false, columnDefinition = "user_role_enum")
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;

  // Relations
  @JsonIgnore
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Inventory> inventories;
}
