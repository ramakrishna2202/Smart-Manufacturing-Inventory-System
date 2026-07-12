package com.smims.backend.service;

import com.smims.backend.entity.Supplier;
import com.smims.backend.exception.DuplicateResourceException;
import com.smims.backend.exception.ResourceNotFoundException;
import com.smims.backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found with id: " + id
                        ));
    }

    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.existsBySupplierCode(
                supplier.getSupplierCode())) {

            throw new DuplicateResourceException(
                    "Supplier already exists with code: "
                            + supplier.getSupplierCode()
            );
        }

        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(
            Long id,
            Supplier updatedSupplier) {

        Supplier existingSupplier = getSupplierById(id);

        existingSupplier.setSupplierCode(
                updatedSupplier.getSupplierCode()
        );
        existingSupplier.setSupplierName(
                updatedSupplier.getSupplierName()
        );
        existingSupplier.setContactPerson(
                updatedSupplier.getContactPerson()
        );
        existingSupplier.setPhone(
                updatedSupplier.getPhone()
        );
        existingSupplier.setEmail(
                updatedSupplier.getEmail()
        );
        existingSupplier.setAddress(
                updatedSupplier.getAddress()
        );
        existingSupplier.setActive(
                updatedSupplier.getActive()
        );

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id) {
        Supplier existingSupplier = getSupplierById(id);
        supplierRepository.delete(existingSupplier);
    }
}