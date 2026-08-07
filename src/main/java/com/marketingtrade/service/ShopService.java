package com.marketingtrade.service;

import com.marketingtrade.dto.ShopRequest;
import com.marketingtrade.dto.ShopResponse;
import com.marketingtrade.entity.Shop;
import com.marketingtrade.exception.ResourceNotFoundException;
import com.marketingtrade.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Shop> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Shop getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Shop not found: " + id));
    }

    public Shop create(ShopRequest request) {

        Shop shop = new Shop();

        shop.setShopName(request.shopName());
        shop.setOwnerName(request.ownerName());
        shop.setContactNumber(request.contactNumber());
        shop.setAddress(request.address());
        shop.setLocation(request.location());

        if (request.active() != null) {
            shop.setActive(request.active());
        }
        log.info("Creating new shop with name: {}", request.shopName());
        return repository.save(shop);
    }

    public Shop update(Long id, ShopRequest request) {

        Shop shop = getById(id);
        log.info("Updating shop with id: {}", id);

        shop.setShopName(request.shopName());
        shop.setOwnerName(request.ownerName());
        shop.setContactNumber(request.contactNumber());
        shop.setAddress(request.address());
        shop.setLocation(request.location());

        if (request.active() != null) {
            shop.setActive(request.active());
        }
        log.info("Updating shop with id: {}", id);
        return repository.save(shop);
    }

    public void deactivate(Long id) {
        log.info("Deactivating shop with id: {}", id);

        Shop shop = getById(id);
        shop.setActive(false);

        repository.save(shop);
    }

    // -----------------------------
    // DTO returning methods for controllers
    // -----------------------------

    @Transactional(readOnly = true)
    public java.util.List<ShopResponse> getAllDto() {
        return repository.findAll()
                .stream()
                .map(s -> new ShopResponse(s.getId(), s.getShopName(), s.getOwnerName(), s.getContactNumber(), s.getAddress(), s.getLocation(), s.getActive()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ShopResponse getByIdDto(Long id) {
        Shop s = getById(id);
        return new ShopResponse(s.getId(), s.getShopName(), s.getOwnerName(), s.getContactNumber(), s.getAddress(), s.getLocation(), s.getActive());
    }

    @Transactional
    public ShopResponse createDto(ShopRequest request) {
        Shop s = create(request);
        return new ShopResponse(s.getId(), s.getShopName(), s.getOwnerName(), s.getContactNumber(), s.getAddress(), s.getLocation(), s.getActive());
    }

    @Transactional
    public ShopResponse updateDto(Long id, ShopRequest request) {
        Shop s = update(id, request);
        return new ShopResponse(s.getId(), s.getShopName(), s.getOwnerName(), s.getContactNumber(), s.getAddress(), s.getLocation(), s.getActive());
    }
}
