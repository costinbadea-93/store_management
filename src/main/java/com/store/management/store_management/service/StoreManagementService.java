package com.store.management.store_management.service;

import com.store.management.store_management.exceptions.DataNotFoundException;
import com.store.management.store_management.model.Product;
import com.store.management.store_management.model.Store;
import com.store.management.store_management.repository.ProductRepository;
import com.store.management.store_management.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreManagementService {
    private StoreRepository storeRepository;
    private ProductRepository productRepository;


    public Store addStore(Store store){
        return storeRepository.save(store);
    }

    public Product addProductToStore(Product product, int storeId){
        var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new DataNotFoundException("Store not found for the given id"));
        var storeProducts = store.getProducts();
        storeProducts.add(product);
        product.setStoreList(product.getStoreList());

        storeRepository.save(store);
        return productRepository.save(product);
    }

    public Product findProductInStore(String productName, int storeId){
        var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new DataNotFoundException("Store not found for the given id"));
        var storeProducts = store.getProducts();
        return storeProducts.stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Product for the given name does not exist in store"));
    }
}
