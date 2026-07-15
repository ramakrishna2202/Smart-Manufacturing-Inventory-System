package com.smims.backend.repository;

import com.smims.backend.entity.SupplierMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierMaterialRepository
        extends JpaRepository<SupplierMaterial, Long> {

    boolean existsBySupplierIdAndRawMaterialId(
            Long supplierId,
            Long rawMaterialId
    );

    List<SupplierMaterial> findBySupplierId(Long supplierId);

    List<SupplierMaterial> findByRawMaterialId(Long rawMaterialId);
}