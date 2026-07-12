package com.smims.backend.controller;

import com.smims.backend.entity.Supplier;
import com.smims.backend.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(
            @Valid @RequestBody Supplier supplier) {

        Supplier createdSupplier =
                supplierService.createSupplier(supplier);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody Supplier supplier) {

        return ResponseEntity.ok(
                supplierService.updateSupplier(id, supplier)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();
    }
}