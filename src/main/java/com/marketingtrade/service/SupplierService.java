package com.marketingtrade.service;

import com.marketingtrade.dto.SupplierRequest;
import com.marketingtrade.dto.SupplierResponse;
import com.marketingtrade.entity.Supplier;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        log.info("Finding supplier with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found: " + id));
    }

    @Transactional
    public Supplier create(SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setSupplierName(request.supplierName());
        supplier.setContact(request.contact());
        supplier.setLocation(request.location());
        supplier.setAddress(request.address());

        if (request.active() != null) {
            supplier.setActive(request.active());
        }
        log.info("Creating new supplier with name: {}", request.supplierName());
        return repository.save(supplier);
    }

    @Transactional
    public Supplier update(Long id, SupplierRequest request) {

        Supplier supplier = getById(id);

        supplier.setSupplierName(request.supplierName());
        supplier.setContact(request.contact());
        supplier.setLocation(request.location());
        supplier.setAddress(request.address());

        if (request.active() != null) {
            supplier.setActive(request.active());
        }
        log.info("Updating supplier with ID: {}", supplier.getId());
        return repository.save(supplier);
    }

    @Transactional
    public void deactivate(Long id) {

        Supplier supplier = getById(id);
        log.info("Deactivating supplier with ID: {}", supplier.getId());
        supplier.setActive(false);

        repository.save(supplier);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    @Transactional(readOnly = true)
    public java.util.List<SupplierResponse> getAllDto() {
        return repository.findAll()
                .stream()
                .map(s -> new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive()))
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse getByIdDto(Long id) {
        Supplier s = getById(id);
        return new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive());
    }

    @Transactional
    public SupplierResponse createDto(SupplierRequest request) {
        Supplier s = create(request);
        return new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive());
    }

    @Transactional
    public SupplierResponse updateDto(Long id, SupplierRequest request) {
        Supplier s = update(id, request);
        return new SupplierResponse(s.getId(), s.getSupplierName(), s.getContact(), s.getLocation(), s.getAddress(), s.getActive());
    }
}
