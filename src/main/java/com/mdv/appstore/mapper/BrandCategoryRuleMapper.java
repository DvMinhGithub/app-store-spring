package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandCategoryRuleMapper {
    void insert(@Param("brandId") Long brandId, @Param("categoryId") Long categoryId);

    void deleteByBrandId(@Param("brandId") Long brandId);

    void deleteByCategoryId(@Param("categoryId") Long categoryId);

    List<Long> getBrandIdsByCategoryId(Long categoryId);

    List<Long> getCategoryIdsByBrandId(Long brandId);

    boolean exists(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);
}
