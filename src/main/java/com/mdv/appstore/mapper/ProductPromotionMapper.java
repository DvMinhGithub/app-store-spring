package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.ProductPromotionDTO;
import com.mdv.appstore.model.request.ProductPromotionRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductPromotionMapper {
    List<ProductPromotionDTO> findAll();

    ProductPromotionDTO findById(@Param("id") Long id);

    void create(@Param("pr") ProductPromotionRequest productPromotion);

    void update(@Param("id") Long id, @Param("pr") ProductPromotionRequest productPromotion);

    void delete(@Param("id") Long id);

    List<ProductPromotionDTO> findActivePromotions();

    List<ProductPromotionDTO> findByProductId(@Param("productId") Long productId);

    List<ProductPromotionDTO> findActivePromotionsByProductId(@Param("productId") Long productId);
}
