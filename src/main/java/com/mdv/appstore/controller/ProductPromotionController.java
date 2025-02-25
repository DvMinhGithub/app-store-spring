package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.dto.ProductPromotionDTO;
import com.mdv.appstore.model.request.ProductPromotionRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.ProductPromotionService;

@RestController
@RequestMapping("${app.api.base-url}/product-promotion")
@RequiredArgsConstructor
public class ProductPromotionController {
    private static final String SUCCESS_MESSAGE = "Product promotion fetched successfully";
    private final ProductPromotionService productPromotionService;

    @GetMapping
    public ApiResponse<List<ProductPromotionDTO>> findAll() {
        List<ProductPromotionDTO> data = productPromotionService.findAll();
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductPromotionDTO> findById(@PathVariable("id") @NonNull Long id) {
        ProductPromotionDTO data = productPromotionService.findById(id);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid ProductPromotionRequest productPromotion) {
        productPromotionService.create(productPromotion);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductPromotionRequest productPromotion) {
        productPromotionService.update(id, productPromotion);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") @NonNull Long id) {
        productPromotionService.delete(id);
        return ApiResponse.success(SUCCESS_MESSAGE);
    }

    @GetMapping("/active")
    public ApiResponse<List<ProductPromotionDTO>> findActivePromotions() {
        List<ProductPromotionDTO> data = productPromotionService.findActivePromotions();
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductPromotionDTO>> findByProductId(
            @PathVariable("productId") @NonNull Long productId) {
        List<ProductPromotionDTO> data = productPromotionService.findByProductId(productId);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/product/{productId}/active")
    public ApiResponse<List<ProductPromotionDTO>> findActivePromotionsByProductId(
            @PathVariable @NonNull Long productId) {
        List<ProductPromotionDTO> data =
                productPromotionService.findActivePromotionsByProductId(productId);
        return ApiResponse.success(data, SUCCESS_MESSAGE);
    }
}
