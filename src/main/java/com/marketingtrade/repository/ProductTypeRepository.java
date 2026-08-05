package com.marketingtrade.repository;

import com.marketingtrade.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeRepository
        extends JpaRepository<ProductType, Long> {

    List<ProductType> findByProduct_Id(Long productId);

    @Query("""
        select pt
        from ProductType pt
        join fetch pt.product
        order by pt.product.productName, pt.typeName
        """)
    List<ProductType> findAllWithProduct();
}