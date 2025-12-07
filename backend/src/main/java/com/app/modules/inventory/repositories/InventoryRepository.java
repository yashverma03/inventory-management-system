package com.app.modules.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.modules.inventory.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
