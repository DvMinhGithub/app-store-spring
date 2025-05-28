package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.CreateCategoryRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.request.UpdateCategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.PaginationResponse;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);

    void deleteCategory(Long id);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    List<CategoryResponse> findByName(String name);

    PaginationResponse<CategoryResponse> findAllWithPagination(PaginationRequest request);

    PaginationResponse<CategoryResponse> searchByName(PaginationRequest request, String name);
}
