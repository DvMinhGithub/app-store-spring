package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.request.ProductCategoryRequest;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
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
