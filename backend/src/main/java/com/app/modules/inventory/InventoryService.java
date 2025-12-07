package com.app.modules.inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.modules.inventory.dto.CreateInventoryDto;
import com.app.modules.inventory.dto.UpdateInventoryDto;
import com.app.modules.inventory.entities.Inventory;
import com.app.modules.inventory.repositories.InventoryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;

@Service
public class InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ModelMapper modelMapper;

  private List<Inventory> get(Long id) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Inventory> criteriaQuery = criteriaBuilder.createQuery(Inventory.class);
    Root<Inventory> inventory = criteriaQuery.from(Inventory.class);
    inventory.fetch("user", JoinType.INNER);

    // Build predicates list
    List<Predicate> predicates = new ArrayList<>();

    // Always filter out soft-deleted records
    predicates.add(criteriaBuilder.isNull(inventory.get("deletedAt")));

    // Add id filter if provided
    if (id != null) {
      predicates.add(criteriaBuilder.equal(inventory.get("id"), id));
    }

    // Apply all predicates
    criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

    Order orderBy = criteriaBuilder.desc(inventory.get("createdAt"));
    criteriaQuery.select(inventory).distinct(true).orderBy(orderBy);
    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  public List<Inventory> getAll(Long id) {
    return get(null);
  }

  public Inventory getById(Long id) {
    List<Inventory> inventories = get(id);
    if (inventories.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
    }
    return inventories.get(0);
  }

  public void createInventory(CreateInventoryDto dto, Long userId) {
    Inventory inventory = modelMapper.map(dto, Inventory.class);
    inventory.setUserId(userId);
    inventoryRepository.save(inventory);
  }

  public void updateInventory(Long id, UpdateInventoryDto dto) {
    Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
    if (optionalInventory.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
    }
    Inventory inventory = optionalInventory.get();
    modelMapper.map(dto, inventory);
    inventoryRepository.save(inventory);
  }

  public void deleteInventory(Long id) {
    Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
    if (optionalInventory.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
    }

    Inventory inventory = optionalInventory.get();
    inventory.setDeletedAt(LocalDateTime.now());
    inventoryRepository.save(inventory);
  }
}
