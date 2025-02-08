package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.CategoryDTO;
import com.mdv.appstore.model.request.CategoryRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-url}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ApiResponse<List<CategoryDTO>> getAllCategories() {
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Category fetched successfully")
                .data(categoryService.findAll())
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryDTO>> getAllActiveCategories() {
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Category fetched successfully")
                .data(categoryService.findAllActive())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        return ApiResponse.<CategoryDTO>builder()
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
    public ApiResponse<Object> updateCategory(
            @PathVariable("id") Long id, @RequestBody CategoryRequest category) {
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
