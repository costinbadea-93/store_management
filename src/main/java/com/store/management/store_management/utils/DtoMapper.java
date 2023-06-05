package com.store.management.store_management.utils;

import com.store.management.store_management.dto.ProductDto;
import com.store.management.store_management.model.Product;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public Product ProductDtoToProduct(ProductDto productDto){
        return Product.builder()
                .productPrice(productDto.getProductPrice())
                .productName(productDto.getProductName())
                .build();
    }
}
