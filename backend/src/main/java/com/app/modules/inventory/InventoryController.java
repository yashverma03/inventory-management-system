package com.app.modules.inventory;

import org.springframework.web.bind.annotation.RestController;

import com.app.modules.inventory.entities.Inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventories")
@Tag(name = "Inventories", description = "Inventory management APIs")
public class InventoryController {
  @Autowired
  private InventoryService inventoryService;

  @Operation(summary = "Get all inventories", description = "Get all inventories")
  @GetMapping
  public ResponseEntity<List<Inventory>> getAll() {
    return ResponseEntity.ok(inventoryService.getAll(null));
  }

  @Operation(summary = "Get an inventory by id", description = "Get an inventory by id")
  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getById(@PathVariable Long id) {
    return ResponseEntity.ok(inventoryService.getById(id));
  }

}
