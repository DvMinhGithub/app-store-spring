package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.appstore.dto.request.ProductPromotionRequest;
import com.mdv.appstore.dto.response.ProductPromotionResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductPromotionMapper;
import com.mdv.appstore.service.ProductPromotionService;
import com.mdv.appstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPromotionServiceImpl implements ProductPromotionService {
    private final ProductPromotionMapper productPromotionMapper;
    private final ProductService productService;

    public List<ProductPromotionResponse> findAll() {
        return productPromotionMapper.findAll();
    }

    public ProductPromotionResponse findById(Long id) {
        ProductPromotionResponse product = productPromotionMapper.findById(id);
        if (product == null) {
            throw new DataNotFoundException("Product promotion not found");
        }
        return product;
    }

    public void create(ProductPromotionRequest productPromotion) {
        productService.findById(productPromotion.getProductId());
        productPromotionMapper.create(productPromotion);
    }

    public void update(Long id, ProductPromotionRequest productPromotion) {
        findById(id);
        productPromotionMapper.update(id, productPromotion);
    }

    public void delete(Long id) {
        findById(id);
        productPromotionMapper.delete(id);
    }

    public List<ProductPromotionResponse> findActivePromotions() {
        return productPromotionMapper.findActivePromotions();
    }

    public List<ProductPromotionResponse> findByProductId(Long productId) {
        return productPromotionMapper.findByProductId(productId);
    }

    public List<ProductPromotionResponse> findActivePromotionsByProductId(Long productId) {
        return productPromotionMapper.findActivePromotionsByProductId(productId);
    }
}
