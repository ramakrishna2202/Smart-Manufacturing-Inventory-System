package com.smims.backend.service;

import com.smims.backend.entity.PurchaseOrder;
import com.smims.backend.entity.PurchaseOrderStatus;
import com.smims.backend.entity.RawMaterial;
import com.smims.backend.entity.Supplier;
import com.smims.backend.exception.DuplicateResourceException;
import com.smims.backend.exception.ResourceNotFoundException;
import com.smims.backend.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierService supplierService;
    private final RawMaterialService rawMaterialService;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierService supplierService,
            RawMaterialService rawMaterialService) {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierService = supplierService;
        this.rawMaterialService = rawMaterialService;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Purchase order not found with id: " + id
                        ));
    }

    public List<PurchaseOrder> getPurchaseOrdersBySupplier(Long supplierId) {

        supplierService.getSupplierById(supplierId);

        return purchaseOrderRepository.findBySupplierId(supplierId);
    }

    public List<PurchaseOrder> getPurchaseOrdersByRawMaterial(Long rawMaterialId) {

        rawMaterialService.getRawMaterialById(rawMaterialId);

        return purchaseOrderRepository.findByRawMaterialId(rawMaterialId);
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(
            PurchaseOrderStatus status) {

        return purchaseOrderRepository.findByStatus(status);
    }

    public PurchaseOrder createPurchaseOrder(
            String purchaseOrderNumber,
            Long supplierId,
            Long rawMaterialId,
            BigDecimal orderQuantity,
            BigDecimal unitPrice) {

        if (purchaseOrderRepository
                .existsByPurchaseOrderNumber(purchaseOrderNumber)) {

            throw new DuplicateResourceException(
                    "Purchase order already exists with number: "
                            + purchaseOrderNumber
            );
        }

        Supplier supplier =
                supplierService.getSupplierById(supplierId);

        RawMaterial rawMaterial =
                rawMaterialService.getRawMaterialById(rawMaterialId);

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setPurchaseOrderNumber(purchaseOrderNumber);
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setRawMaterial(rawMaterial);
        purchaseOrder.setOrderQuantity(orderQuantity);
        purchaseOrder.setUnitPrice(unitPrice);

        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrderStatus(
            Long id,
            PurchaseOrderStatus status) {

        PurchaseOrder purchaseOrder =
                getPurchaseOrderById(id);

        purchaseOrder.setStatus(status);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder =
                getPurchaseOrderById(id);

        purchaseOrderRepository.delete(purchaseOrder);
    }
}