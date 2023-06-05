package com.store.management.store_management.service;


import com.store.management.store_management.exceptions.DataNotFoundException;
import com.store.management.store_management.model.Product;
import com.store.management.store_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreManagementServiceTest {

    @InjectMocks
    public StoreManagementService storeManagementService;

    @Mock
    public ProductRepository productRepository;

    private Product product;

    private Product updatedProduct;

    @BeforeEach
    public void init(){
         product = Product.builder()
                .productId(1)
                .productName("Candy")
                .productPrice(20.4)
                .build();

        updatedProduct = Product.builder()
                .productId(1)
                .productName("Candy")
                .productPrice(30)
                .build();
    }

    @Test
    public void testAddProductTest(){
        //given
        when(productRepository.save(product)).thenReturn(product);


        //we return same product for simplicity
        //it could be other product

        //when
        var savedProduct = productRepository.save(product);
        //then
        verify(productRepository).save(product);
        assertEquals(product.getProductPrice(), savedProduct.getProductPrice(), "should be equals!");
        assertEquals(product.getProductName(), savedProduct.getProductName(), "should be equals!");
    }


    @Test
    public void findProductInStoreHappyFlow(){
        //given
        when(productRepository.findProductByProductName(product.getProductName())).thenReturn(Optional.of(product));

        //called .get on optional doing to the fact that we mocked the behaviour above
        //when
        var foundProduct = productRepository.findProductByProductName(product.getProductName()).get();

        //then
        verify(productRepository).findProductByProductName(product.getProductName());
        assertEquals(product.getProductPrice(), foundProduct.getProductPrice(), "should be equals!");
        assertEquals(product.getProductName(), foundProduct.getProductName(), "should be equals!");
    }


    @Test
    public void findProductInStoreWithError(){
        //given
        when(productRepository.findProductByProductName(product.getProductName()))
                .thenThrow(new DataNotFoundException("Product for the given name was not found"));

        //when + then
        assertThrows(DataNotFoundException.class,
                () -> productRepository.findProductByProductName(product.getProductName()),
        "Product for the given name was not found");

    }

    @Test
    public void changeProductPriceHappyFlow(){
        //given
        when(productRepository.findProductByProductName(product.getProductName())).thenReturn(Optional.of(updatedProduct));

        //called .get on optional doing to the fact that we mocked the behaviour above
        //when
        var foundProduct = productRepository.findProductByProductName(product.getProductName()).get();

        //then
        verify(productRepository).findProductByProductName(product.getProductName());
        assertNotEquals(product.getProductPrice(), foundProduct.getProductPrice(), "should be equals!");
        assertEquals(product.getProductName(), foundProduct.getProductName(), "should be equals!");
        assertEquals(updatedProduct.getProductPrice(), foundProduct.getProductPrice(), "should be equals");
    }


    @Test
    public void deleteProductTest(){
        //given
        doNothing().when(productRepository).deleteByProductName(product.getProductName());

        //when
        productRepository.deleteByProductName(product.getProductName());

        //then
        verify(productRepository).deleteByProductName(product.getProductName());
    }
}
