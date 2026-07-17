package com.smims.backend.repository;

import com.smims.backend.entity.PurchaseOrder;
import com.smims.backend.entity.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    boolean existsByPurchaseOrderNumber(String purchaseOrderNumber);

    Optional<PurchaseOrder> findByPurchaseOrderNumber(
            String purchaseOrderNumber
    );

    List<PurchaseOrder> findBySupplierId(Long supplierId);

    List<PurchaseOrder> findByRawMaterialId(Long rawMaterialId);

    List<PurchaseOrder> findByStatus(PurchaseOrderStatus status);
}