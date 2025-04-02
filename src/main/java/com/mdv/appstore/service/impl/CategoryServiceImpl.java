package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.appstore.dto.request.CategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.CategoryMapper;
import com.mdv.appstore.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_NOT_EXISTS = "Category not exists";
    private static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    private final CategoryMapper categoryMapper;

    public void createCategory(CategoryRequest category) {
        categoryMapper.insert(category);
    }

    public List<CategoryResponse> findAll() {
        return categoryMapper.findAll();
    }

    public List<CategoryResponse> findAllActive() {
        return categoryMapper.findAllActive();
    }

    public CategoryResponse findById(Long id) {
        CategoryResponse category = categoryMapper.findById(id);
        if (category == null || category.isDeleted()) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        return category;
    }

    public void updateCategory(Long id, CategoryRequest category) {
        CategoryResponse categoryResponse = findById(id);
        if (categoryResponse == null || categoryResponse.isDeleted()) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }

        if (categoryMapper.existsByName(category.getName())) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }
        categoryMapper.update(id, category);
    }

    public void deleteCategory(Long id) {
        CategoryResponse category = findById(id);
        if (category == null) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        categoryMapper.softDelete(id);
    }

    public void restoreCategory(Long id) {
        CategoryResponse category = categoryMapper.findById(id);
        if (category == null) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        categoryMapper.restore(id);
    }
}
