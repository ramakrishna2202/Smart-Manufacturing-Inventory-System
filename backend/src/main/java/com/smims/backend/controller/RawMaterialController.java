package com.smims.backend.controller;

import com.smims.backend.entity.RawMaterial;
import com.smims.backend.service.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterial>> getAllRawMaterials() {
        return ResponseEntity.ok(
                rawMaterialService.getAllRawMaterials()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterial> getRawMaterialById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                rawMaterialService.getRawMaterialById(id)
        );
    }

    @PostMapping
    public ResponseEntity<RawMaterial> createRawMaterial(
            @Valid @RequestBody RawMaterial rawMaterial) {

        RawMaterial createdMaterial =
                rawMaterialService.createRawMaterial(rawMaterial);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMaterial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterial> updateRawMaterial(
            @PathVariable Long id,
            @Valid @RequestBody RawMaterial rawMaterial) {

        return ResponseEntity.ok(
                rawMaterialService.updateRawMaterial(id, rawMaterial)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRawMaterial(
            @PathVariable Long id) {

        rawMaterialService.deleteRawMaterial(id);

        return ResponseEntity.noContent().build();
    }
}