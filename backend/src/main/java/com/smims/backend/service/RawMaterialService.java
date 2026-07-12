package com.smims.backend.service;

import com.smims.backend.entity.RawMaterial;
import com.smims.backend.exception.DuplicateResourceException;
import com.smims.backend.exception.ResourceNotFoundException;
import com.smims.backend.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }

    public RawMaterial getRawMaterialById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Raw material not found with id: " + id
                        ));
    }

    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {
        if (rawMaterialRepository.existsByMaterialCode(rawMaterial.getMaterialCode())) {
            throw new DuplicateResourceException(
                    "Raw material already exists with code: "
                            + rawMaterial.getMaterialCode()
            );
        }

        return rawMaterialRepository.save(rawMaterial);
    }

    public RawMaterial updateRawMaterial(Long id, RawMaterial updatedMaterial) {
        RawMaterial existingMaterial = getRawMaterialById(id);

        existingMaterial.setMaterialCode(updatedMaterial.getMaterialCode());
        existingMaterial.setMaterialName(updatedMaterial.getMaterialName());
        existingMaterial.setCategory(updatedMaterial.getCategory());
        existingMaterial.setUnit(updatedMaterial.getUnit());
        existingMaterial.setCurrentStock(updatedMaterial.getCurrentStock());
        existingMaterial.setMinimumStockLevel(updatedMaterial.getMinimumStockLevel());
        existingMaterial.setUnitCost(updatedMaterial.getUnitCost());
        existingMaterial.setStorageLocation(updatedMaterial.getStorageLocation());
        existingMaterial.setActive(updatedMaterial.getActive());

        return rawMaterialRepository.save(existingMaterial);
    }

    public void deleteRawMaterial(Long id) {
        RawMaterial existingMaterial = getRawMaterialById(id);
        rawMaterialRepository.delete(existingMaterial);
    }
}