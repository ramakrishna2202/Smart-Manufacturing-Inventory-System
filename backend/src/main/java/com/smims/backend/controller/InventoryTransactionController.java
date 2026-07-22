package com.smims.backend.controller;

import com.smims.backend.entity.InventoryTransaction;
import com.smims.backend.entity.InventoryTransactionType;
import com.smims.backend.service.InventoryTransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService) {
        this.inventoryTransactionService = inventoryTransactionService;
    }

    @PostMapping
    public InventoryTransaction createTransaction(
            @Valid @RequestBody InventoryTransaction transaction) {
        return inventoryTransactionService.createTransaction(transaction);
    }

    @GetMapping
    public List<InventoryTransaction> getAllTransactions() {
        return inventoryTransactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public InventoryTransaction getTransactionById(@PathVariable Long id) {
        return inventoryTransactionService.getTransactionById(id);
    }

    @GetMapping("/raw-material/{rawMaterialId}")
    public List<InventoryTransaction> getTransactionsByRawMaterial(
            @PathVariable Long rawMaterialId) {
        return inventoryTransactionService.getTransactionsByRawMaterial(rawMaterialId);
    }

    @GetMapping("/type/{type}")
    public List<InventoryTransaction> getTransactionsByType(
            @PathVariable InventoryTransactionType type) {
        return inventoryTransactionService.getTransactionsByType(type);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        inventoryTransactionService.deleteTransaction(id);
    }
}