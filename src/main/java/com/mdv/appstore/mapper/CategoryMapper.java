package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.CategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;

@Mapper
public interface CategoryMapper {
    List<CategoryResponse> findAll();

    List<CategoryResponse> findAllActive();

    CategoryResponse findById(Long id);

    boolean existsByName(String name);

    void insert(@Param("category") CategoryRequest category);

    void update(@Param("id") Long id, @Param("category") CategoryRequest category);

    void restore(@Param("id") Long id);

    void softDelete(Long id);
}
