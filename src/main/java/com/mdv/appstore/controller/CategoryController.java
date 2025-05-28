package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdv.appstore.dto.request.CreateCategoryRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.request.UpdateCategoryRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.PaginationResponse;
import com.mdv.appstore.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private static final String CATEGORY_CREATED_SUCCESS = "Category created successfully";
    private static final String CATEGORY_FETCHED_SUCCESS = "Category fetched successfully";
    private static final String CATEGORY_UPDATED_SUCCESS = "Category updated successfully";
    private static final String CATEGORY_DELETED_SUCCESS = "Category deleted successfully";

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.success(categoryService.findAll(), CATEGORY_FETCHED_SUCCESS);
    }

    @GetMapping()
    public ApiResponse<PaginationResponse<CategoryResponse>> getAllCategories(@Valid PaginationRequest request) {
        return ApiResponse.success(categoryService.findAllWithPagination(request), CATEGORY_FETCHED_SUCCESS);
    }

    @GetMapping("/search")
    public ApiResponse<PaginationResponse<CategoryResponse>> searchCategories(
            @Valid PaginationRequest request, @RequestParam("name") String name) {
        return ApiResponse.success(categoryService.searchByName(request, name), CATEGORY_FETCHED_SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ApiResponse.success(categoryService.findById(id), CATEGORY_FETCHED_SUCCESS);
    }

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        return ApiResponse.success(categoryService.createCategory(request), CATEGORY_CREATED_SUCCESS);
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable("id") Long id, @RequestBody @Valid UpdateCategoryRequest request) {
        return ApiResponse.success(categoryService.updateCategory(id, request), CATEGORY_UPDATED_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success(CATEGORY_DELETED_SUCCESS);
    }
}
