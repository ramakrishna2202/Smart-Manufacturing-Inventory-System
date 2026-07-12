package com.smims.backend.repository;

import com.smims.backend.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    Optional<RawMaterial> findByMaterialCode(String materialCode);

    boolean existsByMaterialCode(String materialCode);
}