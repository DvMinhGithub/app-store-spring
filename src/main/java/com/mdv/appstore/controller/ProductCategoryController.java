package com.mdv.appstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.request.ProductCategoryRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.ProductCategoryService;

@RestController
@RequestMapping("${app.api.base-url}")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/products/{id}/categories")
    public ApiResponse<Object> create(
            @NonNull @PathVariable("id") Long productId,
            @Valid @RequestBody ProductCategoryRequest request) {
        productCategoryService.create(productId, request.getCategoryId());
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Category added to product successfully")
                .build();
    }

    @GetMapping("/products/{id}/categories")
    public ApiResponse<Object> getCategoriesByProductId(@PathVariable("id") Long productId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Categories retrieved successfully")
                .data(productCategoryService.getCategoriesByProductId(productId))
                .build();
    }

    @GetMapping("/categories/{id}/products")
    public ApiResponse<Object> getProductsByCategoryId(@PathVariable("id") Long categoryId) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .data(productCategoryService.getProductsByCategoryId(categoryId))
                .build();
    }

    @DeleteMapping("/products/{productId}/categories/{categoryId}")
    public ApiResponse<Object> delete(
            @PathVariable("productId") Long productId,
            @PathVariable("categoryId") Long categoryId) {
        productCategoryService.delete(productId, categoryId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Category removed from product successfully")
                .build();
    }
}
