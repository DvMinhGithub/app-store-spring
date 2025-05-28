package com.mdv.appstore.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.CreateCategoryRequest;
import com.mdv.appstore.dto.request.UpdateCategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;

@Mapper
public interface CategoryMapper {

    Long insert(@Param("category") CreateCategoryRequest category);

    void update(@Param("id") Long id, @Param("category") UpdateCategoryRequest category);

    void delete(@Param("id") Long id);

    Optional<CategoryResponse> findById(@Param("id") Long id);

    List<CategoryResponse> findAll();

    List<CategoryResponse> searchByName(@Param("name") String name);

    Optional<Long> findIdByName(@Param("name") String name);

    List<CategoryResponse> findAllWithPagination(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sortBy") String sortBy,
            @Param("sortDirection") String sortDirection);

    long countAll();

    List<CategoryResponse> searchByNameWithPagination(
            @Param("name") String name,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sortBy") String sortBy,
            @Param("sortDirection") String sortDirection);

    long countByName(@Param("name") String name);

    boolean existsByName(@Param("name") String name);
}
