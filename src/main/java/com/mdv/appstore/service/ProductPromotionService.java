package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.ProductPromotionRequest;
import com.mdv.appstore.dto.response.ProductPromotionResponse;

public interface ProductPromotionService {

    List<ProductPromotionResponse> findAll();

    ProductPromotionResponse findById(Long id);

    void create(ProductPromotionRequest productPromotion);

    void update(Long id, ProductPromotionRequest productPromotion);

    void delete(Long id);

    List<ProductPromotionResponse> findActivePromotions();

    List<ProductPromotionResponse> findByProductId(Long productId);

    List<ProductPromotionResponse> findActivePromotionsByProductId(Long productId);
}
