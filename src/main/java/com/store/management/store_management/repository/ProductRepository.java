package com.store.management.store_management.repository;

import com.store.management.store_management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findProductByProductName(String productName);
    void deleteByProductName(String productName);
}
