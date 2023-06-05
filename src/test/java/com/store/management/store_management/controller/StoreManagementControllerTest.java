package com.store.management.store_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.management.store_management.dto.ProductDto;
import com.store.management.store_management.model.Product;
import com.store.management.store_management.repository.UserRepository;
import com.store.management.store_management.service.StoreManagementService;
import com.store.management.store_management.utils.DtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Added tests only for two of the methods
 * the rest are similar
 */

@WebMvcTest(StoreManagementController.class)
public class StoreManagementControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public StoreManagementService storeManagementService;

    @MockBean
    public DtoMapper dtoMapper;

    @MockBean
    public UserRepository userRepository;

    @MockBean
    public PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addProductTest() throws Exception {
        var productDto = ProductDto.builder()
                .productName("p1")
                .productPrice(30)
                .build();

        var product = Product.builder()
                .productName("p1")
                .productPrice(30)
                .productId(0)
                .build();

        when(dtoMapper.ProductDtoToProduct(productDto)).thenReturn(product);
        when(storeManagementService.addProduct(product)).thenReturn(product);

        mockMvc.perform(post("/store/product/new")
                .with(csrf())
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("p1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(30));

    }


    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void changePriceTest() throws Exception {

        var product = Product.builder()
                .productName("p1")
                .productPrice(40)
                .productId(1)
                .build();

        when(storeManagementService.changeProductPrice(product.getProductName(), product.getProductPrice())).thenReturn(product);

       var result =  mockMvc.perform(patch("/store/changeProductPrice")
                .param("productName", product.getProductName())
                .param("newPrice", "40")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
               .andReturn();

        var mappedResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        assertEquals(mappedResponse.getProductName(), product.getProductName(), "should be equals!");
        assertEquals(mappedResponse.getProductPrice(), product.getProductPrice(), "should be equals!");
    }
}
