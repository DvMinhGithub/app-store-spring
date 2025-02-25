package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.CategoryDTO;
import com.mdv.appstore.model.request.CategoryRequest;

@Mapper
public interface CategoryMapper {
    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllActive();

    CategoryDTO findById(Long id);

    boolean existsByName(String name);

    void insert(@Param("category") CategoryRequest category);

    void update(@Param("id") Long id, @Param("category") CategoryRequest category);

    void restore(@Param("id") Long id);

    void softDelete(Long id);
}
