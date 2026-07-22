package com.smims.backend.repository;

import com.smims.backend.entity.InventoryTransaction;
import com.smims.backend.entity.InventoryTransactionType;
import com.smims.backend.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    List<InventoryTransaction> findByRawMaterial(RawMaterial rawMaterial);

    List<InventoryTransaction> findByTransactionType(InventoryTransactionType transactionType);

    List<InventoryTransaction> findByRawMaterialId(Long rawMaterialId);
}