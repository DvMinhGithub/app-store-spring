package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.response.ProductAttributeResponse;

@Mapper
public interface ProductAttributeMapper {
    void create(@Param("pad") ProductAttributeResponse productAttributeResponse);

    ProductAttributeResponse findById(Long id);

    List<ProductAttributeResponse> findAll();

    List<ProductAttributeResponse> findAttributesByProductId(Long productId);

    void update(Long id, @Param("pad") ProductAttributeResponse productAttributeResponse);

    void delete(Long id);
}
