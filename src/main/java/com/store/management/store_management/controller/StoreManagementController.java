package com.store.management.store_management.controller;

import com.store.management.store_management.dto.ProductDto;
import com.store.management.store_management.model.Product;
import com.store.management.store_management.service.StoreManagementService;
import com.store.management.store_management.utils.DtoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@AllArgsConstructor
@RequestMapping("/store")
@Validated
public class StoreManagementController {

    private StoreManagementService storeManagementService;
    private DtoMapper dtoMapper;


    @PostMapping("/product/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> addProductToStore(@RequestBody @Valid ProductDto productDto) {
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromPath("/product")
                        .queryParam("productName", productDto.getProductName())
                        .buildAndExpand().toUri())
                .body(storeManagementService.addProduct(dtoMapper.ProductDtoToProduct(productDto)));
    }


    @GetMapping("/product")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Product> findProductInStore(
            @RequestParam
            @NotNull(message = "Product name cannot be null")
                    String productName) {
        return ResponseEntity.ok()
                .body(storeManagementService.findProductInStore(productName));

    }

    @PatchMapping("/changeProductPrice")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> changeProductPrice(
            @RequestParam @NotNull(message = "Product name cannot be null")
            String productName,
            @RequestParam @Min(value = 1, message = "New price must be a positive value")
            double newPrice) {
        return ResponseEntity.ok()
                .body(storeManagementService.changeProductPrice(productName, newPrice));
    }

    @DeleteMapping("/deleteProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteProduct(
            @RequestParam  @NotNull(message = "Product name cannot be null")
            String productName) {
        storeManagementService.deleteProduct(productName);
        return ResponseEntity.ok()
                .body("Product was successfully deleted !!!");
    }
}
