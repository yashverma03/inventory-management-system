package com.app.modules.dto;

import com.app.modules.inventory.entities.Inventory;
import com.app.modules.users.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInventoryResponseDto {
  private Inventory inventory;
  private User user;
}
