package com.smims.backend.service;

import com.smims.backend.entity.InventoryTransaction;
import com.smims.backend.entity.InventoryTransactionType;
import com.smims.backend.entity.RawMaterial;
import com.smims.backend.exception.ResourceNotFoundException;
import com.smims.backend.repository.InventoryTransactionRepository;
import com.smims.backend.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class InventoryTransactionService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public InventoryTransactionService(
            InventoryTransactionRepository inventoryTransactionRepository,
            RawMaterialRepository rawMaterialRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public InventoryTransaction createTransaction(InventoryTransaction transaction) {

        RawMaterial rawMaterial = rawMaterialRepository.findById(
                        transaction.getRawMaterial().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Raw Material not found with id: "
                                        + transaction.getRawMaterial().getId()));

        BigDecimal currentStock = rawMaterial.getCurrentStock();
        BigDecimal quantity = transaction.getQuantity();

        if (transaction.getTransactionType() == InventoryTransactionType.STOCK_IN) {
            rawMaterial.setCurrentStock(currentStock.add(quantity));
        } else if (transaction.getTransactionType() == InventoryTransactionType.STOCK_OUT) {

            if (currentStock.compareTo(quantity) < 0) {
                throw new IllegalArgumentException("Insufficient stock available");
            }

            rawMaterial.setCurrentStock(currentStock.subtract(quantity));

        } else {
            rawMaterial.setCurrentStock(quantity);
        }

        rawMaterialRepository.save(rawMaterial);

        transaction.setRawMaterial(rawMaterial);

        return inventoryTransactionRepository.save(transaction);
    }

    public List<InventoryTransaction> getAllTransactions() {
        return inventoryTransactionRepository.findAll();
    }

    public InventoryTransaction getTransactionById(Long id) {
        return inventoryTransactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory Transaction not found with id: " + id));
    }

    public List<InventoryTransaction> getTransactionsByRawMaterial(Long rawMaterialId) {
        return inventoryTransactionRepository.findByRawMaterialId(rawMaterialId);
    }

    public List<InventoryTransaction> getTransactionsByType(
            InventoryTransactionType transactionType) {
        return inventoryTransactionRepository.findByTransactionType(transactionType);
    }

    public void deleteTransaction(Long id) {
        InventoryTransaction transaction = getTransactionById(id);
        inventoryTransactionRepository.delete(transaction);
    }
}