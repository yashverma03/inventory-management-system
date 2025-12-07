package com.app.modules.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.modules.inventory.entities.Inventory;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  @Override
  @Query("SELECT inventory FROM Inventory inventory WHERE inventory.id = :id AND inventory.deletedAt IS NULL")
  Optional<Inventory> findById(@Param("id") Long id);
}
