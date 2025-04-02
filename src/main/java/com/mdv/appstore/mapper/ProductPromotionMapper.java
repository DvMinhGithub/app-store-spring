package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.ProductPromotionRequest;
import com.mdv.appstore.dto.response.ProductPromotionResponse;

@Mapper
public interface ProductPromotionMapper {
    List<ProductPromotionResponse> findAll();

    ProductPromotionResponse findById(@Param("id") Long id);

    void create(@Param("pr") ProductPromotionRequest productPromotion);

    void update(@Param("id") Long id, @Param("pr") ProductPromotionRequest productPromotion);

    void delete(@Param("id") Long id);

    List<ProductPromotionResponse> findActivePromotions();

    List<ProductPromotionResponse> findByProductId(@Param("productId") Long productId);

    List<ProductPromotionResponse> findActivePromotionsByProductId(@Param("productId") Long productId);
}
