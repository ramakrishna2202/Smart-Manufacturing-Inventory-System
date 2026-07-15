package com.smims.backend.service;

import com.smims.backend.entity.RawMaterial;
import com.smims.backend.entity.Supplier;
import com.smims.backend.entity.SupplierMaterial;
import com.smims.backend.exception.DuplicateResourceException;
import com.smims.backend.exception.ResourceNotFoundException;
import com.smims.backend.repository.SupplierMaterialRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SupplierMaterialService {

    private final SupplierMaterialRepository supplierMaterialRepository;
    private final SupplierService supplierService;
    private final RawMaterialService rawMaterialService;

    public SupplierMaterialService(
            SupplierMaterialRepository supplierMaterialRepository,
            SupplierService supplierService,
            RawMaterialService rawMaterialService) {

        this.supplierMaterialRepository = supplierMaterialRepository;
        this.supplierService = supplierService;
        this.rawMaterialService = rawMaterialService;
    }

    public List<SupplierMaterial> getAllSupplierMaterials() {
        return supplierMaterialRepository.findAll();
    }

    public SupplierMaterial getSupplierMaterialById(Long id) {
        return supplierMaterialRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier-material relationship not found with id: " + id
                        ));
    }

    public List<SupplierMaterial> getMaterialsBySupplier(Long supplierId) {

        // Verify that the supplier exists
        supplierService.getSupplierById(supplierId);

        return supplierMaterialRepository.findBySupplierId(supplierId);
    }

    public List<SupplierMaterial> getSuppliersByRawMaterial(Long rawMaterialId) {

        // Verify that the raw material exists
        rawMaterialService.getRawMaterialById(rawMaterialId);

        return supplierMaterialRepository.findByRawMaterialId(rawMaterialId);
    }

    public SupplierMaterial createSupplierMaterial(
            Long supplierId,
            Long rawMaterialId,
            BigDecimal supplierPrice,
            Integer leadTimeDays,
            Boolean preferredSupplier) {

        Supplier supplier =
                supplierService.getSupplierById(supplierId);

        RawMaterial rawMaterial =
                rawMaterialService.getRawMaterialById(rawMaterialId);

        if (supplierMaterialRepository
                .existsBySupplierIdAndRawMaterialId(
                        supplierId,
                        rawMaterialId)) {

            throw new DuplicateResourceException(
                    "Supplier is already linked to raw material"
            );
        }

        SupplierMaterial supplierMaterial =
                new SupplierMaterial();

        supplierMaterial.setSupplier(supplier);
        supplierMaterial.setRawMaterial(rawMaterial);
        supplierMaterial.setSupplierPrice(supplierPrice);
        supplierMaterial.setLeadTimeDays(leadTimeDays);

        supplierMaterial.setPreferredSupplier(
                preferredSupplier != null
                        ? preferredSupplier
                        : false
        );

        supplierMaterial.setActive(true);

        return supplierMaterialRepository.save(supplierMaterial);
    }

    public SupplierMaterial updateSupplierMaterial(
            Long id,
            BigDecimal supplierPrice,
            Integer leadTimeDays,
            Boolean preferredSupplier,
            Boolean active) {

        SupplierMaterial existing =
                getSupplierMaterialById(id);

        existing.setSupplierPrice(supplierPrice);
        existing.setLeadTimeDays(leadTimeDays);
        existing.setPreferredSupplier(preferredSupplier);
        existing.setActive(active);

        return supplierMaterialRepository.save(existing);
    }

    public void deleteSupplierMaterial(Long id) {

        SupplierMaterial existing =
                getSupplierMaterialById(id);

        supplierMaterialRepository.delete(existing);
    }
}