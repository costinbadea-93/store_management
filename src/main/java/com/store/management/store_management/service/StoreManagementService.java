package com.store.management.store_management.service;

import com.store.management.store_management.exceptions.DataNotFoundException;
import com.store.management.store_management.model.Product;
import com.store.management.store_management.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreManagementService {
    private ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product findProductInStore(String productName){
        return productRepository.findProductByProductName(productName)
                .orElseThrow(() -> new DataNotFoundException("Store not found for the given id"));
    }

    public Product changeProductPrice(String productName, double price){
       var product = productRepository.findProductByProductName(productName)
               .orElseThrow(() -> new DataNotFoundException("Store not found for the given id"));
       product.setProductPrice(price);
       return productRepository.save(product);
    }

    public void deleteProduct(String productName){
        productRepository.deleteByProductName(productName);
    }
}
