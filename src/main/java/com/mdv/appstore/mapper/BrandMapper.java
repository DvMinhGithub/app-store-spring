package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.response.BrandResponse;

@Mapper
public interface BrandMapper {
    void createBrand(@Param("brand") BrandRequest brand);

    List<BrandResponse> findAll();

    BrandResponse findById(@Param("id") Long id);

    void updateBrand(@Param("id") Long id, @Param("brand") BrandRequest brand);

    void deleteBrand(@Param("id") Long id);

    boolean existsByName(@Param("name") String name);
}
