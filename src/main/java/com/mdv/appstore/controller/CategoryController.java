package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.CategoryRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("All categories fetched successfully")
                .data(categoryService.findAll())
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllActiveCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Active categories fetched successfully")
                .data(categoryService.findAllActive())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category fetched successfully")
                .data(categoryService.findById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<Object> createCategory(@RequestBody CategoryRequest category) {
        categoryService.createCategory(category);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Category created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest category) {
        categoryService.updateCategory(id, category);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Category updated successfully")
                .build();
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<Object> restoreCategory(@PathVariable("id") Long id) {
        categoryService.restoreCategory(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Category restored successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Category deleted successfully")
                .build();
    }
}
