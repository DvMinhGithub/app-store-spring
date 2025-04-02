package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.ProductResponse;

@Mapper
public interface ProductCategoryMapper {
    void create(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    List<CategoryResponse> getCategoriesByProductId(Long productId);

    List<ProductResponse> getProductsByCategoryId(Long categoryId);

    boolean isExist(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    void delete(@Param("productId") Long productId, @Param("categoryId") Long categoryId);
}
