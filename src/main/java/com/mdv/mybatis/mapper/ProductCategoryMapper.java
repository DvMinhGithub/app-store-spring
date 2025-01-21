package com.mdv.mybatis.mapper;

import com.mdv.mybatis.model.dto.CategoryDTO;
import com.mdv.mybatis.model.dto.ProductDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCategoryMapper {
    void create(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    List<CategoryDTO> getCategoriesByProductId(Long productId);

    List<ProductDTO> getProductsByCategoryId(Long categoryId);

    boolean isExist(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    void delete(@Param("productId") Long productId, @Param("categoryId") Long categoryId);
}
