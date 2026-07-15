package com.smims.backend.controller;

import com.smims.backend.entity.SupplierMaterial;
import com.smims.backend.service.SupplierMaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/supplier-materials")
public class SupplierMaterialController {

    private final SupplierMaterialService supplierMaterialService;

    public SupplierMaterialController(
            SupplierMaterialService supplierMaterialService) {
        this.supplierMaterialService = supplierMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierMaterial>> getAllSupplierMaterials() {
        return ResponseEntity.ok(
                supplierMaterialService.getAllSupplierMaterials()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierMaterial> getSupplierMaterialById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplierMaterialService.getSupplierMaterialById(id)
        );
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SupplierMaterial>> getMaterialsBySupplier(
            @PathVariable Long supplierId) {

        return ResponseEntity.ok(
                supplierMaterialService.getMaterialsBySupplier(supplierId)
        );
    }

    @GetMapping("/raw-material/{rawMaterialId}")
    public ResponseEntity<List<SupplierMaterial>> getSuppliersByRawMaterial(
            @PathVariable Long rawMaterialId) {

        return ResponseEntity.ok(
                supplierMaterialService.getSuppliersByRawMaterial(rawMaterialId)
        );
    }

    @PostMapping
    public ResponseEntity<SupplierMaterial> createSupplierMaterial(
            @Valid @RequestBody SupplierMaterialRequest request) {

        SupplierMaterial created =
                supplierMaterialService.createSupplierMaterial(
                        request.getSupplierId(),
                        request.getRawMaterialId(),
                        request.getSupplierPrice(),
                        request.getLeadTimeDays(),
                        request.getPreferredSupplier()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierMaterial> updateSupplierMaterial(
            @PathVariable Long id,
            @Valid @RequestBody SupplierMaterialUpdateRequest request) {

        return ResponseEntity.ok(
                supplierMaterialService.updateSupplierMaterial(
                        id,
                        request.getSupplierPrice(),
                        request.getLeadTimeDays(),
                        request.getPreferredSupplier(),
                        request.getActive()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplierMaterial(
            @PathVariable Long id) {

        supplierMaterialService.deleteSupplierMaterial(id);

        return ResponseEntity.noContent().build();
    }

    public static class SupplierMaterialRequest {

        @NotNull(message = "Supplier ID is required")
        private Long supplierId;

        @NotNull(message = "Raw material ID is required")
        private Long rawMaterialId;

        @NotNull(message = "Supplier price is required")
        @DecimalMin(
                value = "0.0",
                message = "Supplier price cannot be negative"
        )
        private BigDecimal supplierPrice;

        @Min(
                value = 0,
                message = "Lead time cannot be negative"
        )
        private Integer leadTimeDays;

        private Boolean preferredSupplier;

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

        public BigDecimal getSupplierPrice() {
            return supplierPrice;
        }

        public void setSupplierPrice(BigDecimal supplierPrice) {
            this.supplierPrice = supplierPrice;
        }

        public Integer getLeadTimeDays() {
            return leadTimeDays;
        }

        public void setLeadTimeDays(Integer leadTimeDays) {
            this.leadTimeDays = leadTimeDays;
        }

        public Boolean getPreferredSupplier() {
            return preferredSupplier;
        }

        public void setPreferredSupplier(Boolean preferredSupplier) {
            this.preferredSupplier = preferredSupplier;
        }
    }

    public static class SupplierMaterialUpdateRequest {

        @NotNull(message = "Supplier price is required")
        @DecimalMin(
                value = "0.0",
                message = "Supplier price cannot be negative"
        )
        private BigDecimal supplierPrice;

        @Min(
                value = 0,
                message = "Lead time cannot be negative"
        )
        private Integer leadTimeDays;

        @NotNull(message = "Preferred supplier status is required")
        private Boolean preferredSupplier;

        @NotNull(message = "Active status is required")
        private Boolean active;

        public BigDecimal getSupplierPrice() {
            return supplierPrice;
        }

        public void setSupplierPrice(BigDecimal supplierPrice) {
            this.supplierPrice = supplierPrice;
        }

        public Integer getLeadTimeDays() {
            return leadTimeDays;
        }

        public void setLeadTimeDays(Integer leadTimeDays) {
            this.leadTimeDays = leadTimeDays;
        }

        public Boolean getPreferredSupplier() {
            return preferredSupplier;
        }

        public void setPreferredSupplier(Boolean preferredSupplier) {
            this.preferredSupplier = preferredSupplier;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
    }
}