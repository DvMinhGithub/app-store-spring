package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.ProductPromotionRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.ProductPromotionResponse;
import com.mdv.appstore.service.ProductPromotionService;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/product-promotion")
@RequiredArgsConstructor
public class ProductPromotionController {
    private static final String SUCCESS_MESSAGE = "Product promotion fetched successfully";
    private final ProductPromotionService productPromotionService;

    @GetMapping
    public ApiResponse<List<ProductPromotionResponse>> findAll() {
        List<ProductPromotionResponse> data = productPromotionService.findAll();
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductPromotionResponse> findById(@PathVariable("id") @NonNull Long id) {
        ProductPromotionResponse data = productPromotionService.findById(id);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid ProductPromotionRequest productPromotion) {
        productPromotionService.create(productPromotion);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable("id") Long id, @RequestBody @Valid ProductPromotionRequest productPromotion) {
        productPromotionService.update(id, productPromotion);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") @NonNull Long id) {
        productPromotionService.delete(id);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @GetMapping("/active")
    public ApiResponse<List<ProductPromotionResponse>> findActivePromotions() {
        List<ProductPromotionResponse> data = productPromotionService.findActivePromotions();
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductPromotionResponse>> findByProductId(
            @PathVariable("productId") @NonNull Long productId) {
        List<ProductPromotionResponse> data = productPromotionService.findByProductId(productId);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/product/{productId}/active")
    public ApiResponse<List<ProductPromotionResponse>> findActivePromotionsByProductId(
            @PathVariable @NonNull Long productId) {
        List<ProductPromotionResponse> data = productPromotionService.findActivePromotionsByProductId(productId);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }
}
