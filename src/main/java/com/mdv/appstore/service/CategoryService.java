package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.CategoryMapper;
import com.mdv.appstore.model.dto.CategoryDTO;
import com.mdv.appstore.model.request.CategoryRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    private static final String CATEGORY_NOT_EXISTS = "Category not exists";
    private static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    public void createCategory(CategoryRequest category) {
        categoryMapper.insert(category);
    }

    public List<CategoryDTO> findAll() {
        return categoryMapper.findAll();
    }

    public List<CategoryDTO> findAllActive() {
        return categoryMapper.findAllActive();
    }

    public CategoryDTO findById(Long id) {
        CategoryDTO category = categoryMapper.findById(id);
        if (category == null || category.isDeleted()) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        return category;
    }

    public void updateCategory(Long id, CategoryRequest category) {
        CategoryDTO categoryDTO = findById(id);
        if (categoryDTO == null || categoryDTO.isDeleted()) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }

        if (categoryMapper.existsByName(category.getName())) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }
        categoryMapper.update(id, category);
    }

    public void deleteCategory(Long id) {
        CategoryDTO category = findById(id);
        if (category == null) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        categoryMapper.softDelete(id);
    }

    public void restoreCategory(Long id) {
        CategoryDTO category = categoryMapper.findById(id);
        if (category == null) {
            throw new DataNotFoundException(CATEGORY_NOT_EXISTS);
        }
        categoryMapper.restore(id);
    }
}
