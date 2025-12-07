package com.app.modules.inventory;

import org.springframework.web.bind.annotation.RestController;

import com.app.common.annotations.LoggedInUser;
import com.app.common.dto.MessageResponse;
import com.app.modules.inventory.dto.CreateInventoryDto;
import com.app.modules.inventory.dto.DummyJsonProductsResponseDto;
import com.app.modules.inventory.dto.UpdateInventoryDto;
import com.app.modules.inventory.entities.Inventory;
import com.app.modules.jwt.classes.JwtPayload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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

  @Operation(summary = "Get external products from dummy JSON API", description = "Fetches products from dummy JSON API")
  @GetMapping("/external")
  public ResponseEntity<DummyJsonProductsResponseDto> getExternalInventory() {
    DummyJsonProductsResponseDto response = inventoryService.getExternalInventory();
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get an inventory by id", description = "Get an inventory by id")
  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getById(@PathVariable Long id) {
    return ResponseEntity.ok(inventoryService.getById(id));
  }

  @Operation(summary = "Create a new inventory item", description = "Creates a new inventory item. User ID is taken from the logged-in user.")
  @PostMapping
  public ResponseEntity<MessageResponse> createInventory(
      @Valid @RequestBody CreateInventoryDto dto,
      @Parameter(hidden = true) @LoggedInUser JwtPayload jwtPayload) {
    inventoryService.createInventory(dto, jwtPayload.getUserId());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new MessageResponse("Inventory created successfully"));
  }

  @Operation(summary = "Update an inventory item", description = "Updates an inventory item by id. User ID cannot be updated. All fields are optional.")
  @PatchMapping("/{id}")
  public ResponseEntity<MessageResponse> updateInventory(
      @PathVariable Long id,
      @Valid @RequestBody UpdateInventoryDto dto) {
    inventoryService.updateInventory(id, dto);
    return ResponseEntity.ok(new MessageResponse("Inventory updated successfully"));
  }

  @Operation(summary = "Delete an inventory item", description = "Soft deletes an inventory item by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> deleteInventory(@PathVariable Long id) {
    inventoryService.deleteInventory(id);
    return ResponseEntity.ok(new MessageResponse("Inventory deleted successfully"));
  }

}
