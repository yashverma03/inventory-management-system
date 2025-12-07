package com.app.modules.inventory.entities;

import com.app.common.entities.BaseEntity;
import com.app.modules.users.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "inventories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Inventory extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "description", nullable = true)
  private String description;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "category", nullable = false)
  private String category;

  // Relations
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
