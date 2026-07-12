package com.smims.backend.repository;

import com.smims.backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsBySupplierCode(String supplierCode);
}