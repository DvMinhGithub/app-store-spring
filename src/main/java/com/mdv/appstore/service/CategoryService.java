package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.CategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;

public interface CategoryService {

    void createCategory(CategoryRequest category);

    List<CategoryResponse> findAll();

    List<CategoryResponse> findAllActive();

    CategoryResponse findById(Long id);

    void updateCategory(Long id, CategoryRequest category);

    void deleteCategory(Long id);

    void restoreCategory(Long id);
}
