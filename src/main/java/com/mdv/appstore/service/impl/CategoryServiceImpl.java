package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.appstore.dto.request.CreateCategoryRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.request.UpdateCategoryRequest;
import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.PaginationResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.BrandCategoryRuleMapper;
import com.mdv.appstore.mapper.BrandMapper;
import com.mdv.appstore.mapper.CategoryMapper;
import com.mdv.appstore.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_NOT_EXISTS = "Category not exists";
    private static final String BRAND_NOT_EXISTS = "Brand not exists";
    private static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    private final CategoryMapper categoryMapper;
    private final BrandCategoryRuleMapper brandCategoryRuleMapper;
    private final BrandMapper brandMapper;

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        validateBrandExists(request.getBrandId());
        validateCategoryNameNotExists(request.getName());

        Long categoryId = categoryMapper.insert(request);
        brandCategoryRuleMapper.insert(request.getBrandId(), categoryId);

        return categoryMapper.findById(categoryId).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_EXISTS));
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        CategoryResponse existingCategory =
                categoryMapper.findById(id).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_EXISTS));

        if (!existingCategory.getName().equals(request.getName())) {
            validateCategoryNameNotExists(request.getName());
        }

        categoryMapper.update(id, request);

        return categoryMapper.findById(id).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_EXISTS));
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryMapper.findById(id).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_EXISTS));
        brandCategoryRuleMapper.deleteByCategoryId(id);
        categoryMapper.delete(id);
    }

    public CategoryResponse findById(Long id) {
        return categoryMapper.findById(id).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_EXISTS));
    }

    public List<CategoryResponse> findAll() {
        return categoryMapper.findAll();
    }

    public List<CategoryResponse> findByName(String name) {
        return categoryMapper.searchByName(name);
    }

    public PaginationResponse<CategoryResponse> findAllWithPagination(PaginationRequest request) {
        int offset = (request.getPage() - 1) * request.getSize();
        List<CategoryResponse> categories = categoryMapper.findAllWithPagination(
                offset, request.getSize(), request.getSortBy(), request.getSortDirection());
        long total = categoryMapper.countAll();
        return PaginationResponse.of(categories, total, request.getPage(), request.getSize());
    }

    public PaginationResponse<CategoryResponse> searchByName(PaginationRequest request, String name) {
        int offset = (request.getPage() - 1) * request.getSize();
        List<CategoryResponse> categories = categoryMapper.searchByNameWithPagination(
                name, offset, request.getSize(), request.getSortBy(), request.getSortDirection());
        long total = categoryMapper.countByName(name);
        return PaginationResponse.of(categories, total, request.getPage(), request.getSize());
    }

    private void validateBrandExists(Long brandId) {
        if (brandMapper.findById(brandId) == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
    }

    private void validateCategoryNameNotExists(String name) {
        if (categoryMapper.existsByName(name)) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }
    }
}
