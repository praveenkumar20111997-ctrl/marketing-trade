package com.marketingtrade.service;

import com.marketingtrade.dto.SupplierRequest;
import com.marketingtrade.entity.Supplier;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Supplier> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Supplier getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found: " + id));
    }

    public Supplier create(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(request.supplierName());
        supplier.setContact(request.contact());
        supplier.setLocation(request.location());
        supplier.setAddress(request.address());

        if (request.active() != null) {
            supplier.setActive(request.active());
        }

        return repository.save(supplier);
    }

    public Supplier update(Long id, SupplierRequest request) {

        Supplier supplier = getById(id);

        supplier.setSupplierName(request.supplierName());
        supplier.setContact(request.contact());
        supplier.setLocation(request.location());
        supplier.setAddress(request.address());

        if (request.active() != null) {
            supplier.setActive(request.active());
        }

        return repository.save(supplier);
    }

    public void deactivate(Long id) {

        Supplier supplier = getById(id);
        supplier.setActive(false);

        repository.save(supplier);
    }
}