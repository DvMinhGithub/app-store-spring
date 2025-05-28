package com.mdv.appstore.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.response.BrandResponse;

@Mapper
public interface BrandMapper {
    void createBrand(@Param("brand") BrandRequest brand);

    List<BrandResponse> findAll();

    List<BrandResponse> findAllWithPagination(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sortBy") String sortBy,
            @Param("sortDirection") String sortDirection);

    long countAll();

    BrandResponse findById(@Param("id") Long id);

    List<BrandResponse> findByName(@Param("name") String name);

    BrandResponse findByCategoryId(@Param("categoryId") Long categoryId);

    void updateBrand(@Param("id") Long id, @Param("brand") BrandRequest brand);

    void softDeleteBrand(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);

    void restoreBrand(@Param("id") Long id);

    boolean existsByName(@Param("name") String name);
}
