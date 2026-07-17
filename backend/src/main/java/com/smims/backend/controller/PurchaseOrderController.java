package com.smims.backend.controller;

import com.smims.backend.entity.PurchaseOrder;
import com.smims.backend.entity.PurchaseOrderStatus;
import com.smims.backend.service.PurchaseOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(
                purchaseOrderService.getAllPurchaseOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseOrderService.getPurchaseOrderById(id)
        );
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersBySupplier(
            @PathVariable Long supplierId) {

        return ResponseEntity.ok(
                purchaseOrderService
                        .getPurchaseOrdersBySupplier(supplierId)
        );
    }

    @GetMapping("/raw-material/{rawMaterialId}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByRawMaterial(
            @PathVariable Long rawMaterialId) {

        return ResponseEntity.ok(
                purchaseOrderService
                        .getPurchaseOrdersByRawMaterial(rawMaterialId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByStatus(
            @PathVariable PurchaseOrderStatus status) {

        return ResponseEntity.ok(
                purchaseOrderService.getPurchaseOrdersByStatus(status)
        );
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest request) {

        PurchaseOrder created =
                purchaseOrderService.createPurchaseOrder(
                        request.getPurchaseOrderNumber(),
                        request.getSupplierId(),
                        request.getRawMaterialId(),
                        request.getOrderQuantity(),
                        request.getUnitPrice()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseOrderStatusRequest request) {

        return ResponseEntity.ok(
                purchaseOrderService.updatePurchaseOrderStatus(
                        id,
                        request.getStatus()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(
            @PathVariable Long id) {

        purchaseOrderService.deletePurchaseOrder(id);

        return ResponseEntity.noContent().build();
    }

    public static class PurchaseOrderRequest {

        @NotBlank(message = "Purchase order number is required")
        private String purchaseOrderNumber;

        @NotNull(message = "Supplier ID is required")
        private Long supplierId;

        @NotNull(message = "Raw material ID is required")
        private Long rawMaterialId;

        @NotNull(message = "Order quantity is required")
        @DecimalMin(
                value = "0.001",
                message = "Order quantity must be greater than zero"
        )
        private BigDecimal orderQuantity;

        @NotNull(message = "Unit price is required")
        @DecimalMin(
                value = "0.0",
                message = "Unit price cannot be negative"
        )
        private BigDecimal unitPrice;

        public String getPurchaseOrderNumber() {
            return purchaseOrderNumber;
        }

        public void setPurchaseOrderNumber(String purchaseOrderNumber) {
            this.purchaseOrderNumber = purchaseOrderNumber;
        }

        public Long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Long supplierId) {
            this.supplierId = supplierId;
        }

        public Long getRawMaterialId() {
            return rawMaterialId;
        }

        public void setRawMaterialId(Long rawMaterialId) {
            this.rawMaterialId = rawMaterialId;
        }

        public BigDecimal getOrderQuantity() {
            return orderQuantity;
        }

        public void setOrderQuantity(BigDecimal orderQuantity) {
            this.orderQuantity = orderQuantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }
    }

    public static class PurchaseOrderStatusRequest {

        @NotNull(message = "Purchase order status is required")
        private PurchaseOrderStatus status;

        public PurchaseOrderStatus getStatus() {
            return status;
        }

        public void setStatus(PurchaseOrderStatus status) {
            this.status = status;
        }
    }
}