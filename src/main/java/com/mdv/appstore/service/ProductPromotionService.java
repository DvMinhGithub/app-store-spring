package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductPromotionMapper;
import com.mdv.appstore.model.dto.ProductPromotionDTO;
import com.mdv.appstore.model.request.ProductPromotionRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPromotionService {
    private final ProductPromotionMapper productPromotionMapper;
    private final ProductService productService;

    public List<ProductPromotionDTO> findAll() {
        return productPromotionMapper.findAll();
    }

    public ProductPromotionDTO findById(Long id) {
        ProductPromotionDTO product = productPromotionMapper.findById(id);
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

    public List<ProductPromotionDTO> findActivePromotions() {
        return productPromotionMapper.findActivePromotions();
    }

    public List<ProductPromotionDTO> findByProductId(Long productId) {
        return productPromotionMapper.findByProductId(productId);
    }

    public List<ProductPromotionDTO> findActivePromotionsByProductId(Long productId) {
        return productPromotionMapper.findActivePromotionsByProductId(productId);
    }
}
