package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.ProductAttributeResponse;
import com.mdv.appstore.service.ProductAtrributeService;

@RestController
@RequestMapping("${app.api.base-url}/product-attribute")
@RequiredArgsConstructor
public class ProductAtrributeController {
    private final ProductAtrributeService productAtrributeService;

    private static final String PRODUCT_ATTRIBUTE_FETCHED_SUCCESSFULLY_MESSAGE =
            "Product attribute fetched successfully";

    @GetMapping
    public ApiResponse<List<ProductAttributeResponse>> findAll() {
        return ApiResponse.<List<ProductAttributeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(PRODUCT_ATTRIBUTE_FETCHED_SUCCESSFULLY_MESSAGE)
                .data(productAtrributeService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductAttributeResponse> findById(@PathVariable("id") Long id) {
        return ApiResponse.<ProductAttributeResponse>builder()
                .code(HttpStatus.OK.value())
                .message(PRODUCT_ATTRIBUTE_FETCHED_SUCCESSFULLY_MESSAGE)
                .data(productAtrributeService.findById(id))
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductAttributeResponse>> findAttributesByProductId(
            @PathVariable("productId") Long productId) {
        return ApiResponse.<List<ProductAttributeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(PRODUCT_ATTRIBUTE_FETCHED_SUCCESSFULLY_MESSAGE)
                .data(productAtrributeService.findAttributesByProductId(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<Object> createProductAttribute(
            @RequestBody @Valid ProductAttributeResponse productAttribute) {
        productAtrributeService.createProductAtrribute(productAttribute);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Product attribute created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateProductAttribute(
            @PathVariable("id") Long id, @RequestBody @Valid ProductAttributeResponse productAttribute) {
        productAtrributeService.updateProductAtrribute(id, productAttribute);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProductAttribute(@PathVariable("id") Long id) {
        productAtrributeService.deleteProductAtrribute(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute deleted successfully")
                .build();
    }
}
