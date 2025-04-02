package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.ProductResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.ProductCategoryMapper;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductCategoryService {

    ProductCategoryMapper productCategoryMapper;
    ProductService productService;
    CategoryService categoryService;

    public void create(Long productId, Long categoryId) {
        validateProductAndCategory(productId, categoryId);

        if (isExist(productId, categoryId)) {
            throw new DuplicateEntryException(
                    String.format(
                            "Product with ID %d is already associated with category ID %d.",
                            productId, categoryId));
        }

        productCategoryMapper.create(productId, categoryId);
    }

    public List<CategoryResponse> getCategoriesByProductId(Long productId) {
        validateProduct(productId);
        return productCategoryMapper.getCategoriesByProductId(productId);
    }

    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        validateCategory(categoryId);
        return productCategoryMapper.getProductsByCategoryId(categoryId);
    }

    public void delete(Long productId, Long categoryId) {
        validateProductAndCategory(productId, categoryId);

        if (!isExist(productId, categoryId)) {
            throw new DataNotFoundException(
                    String.format(
                            "Product with ID %d and category ID %d association not found.",
                            productId, categoryId));
        }

        productCategoryMapper.delete(productId, categoryId);
    }

    private boolean isExist(@NonNull Long productId, @NonNull Long categoryId) {
        return productCategoryMapper.isExist(productId, categoryId);
    }

    private void validateProductAndCategory(@NonNull Long productId, @NonNull Long categoryId) {
        validateProduct(productId);
        validateCategory(categoryId);
    }

    private void validateProduct(Long productId) {
        if (productService.findById(productId) == null) {
            throw new IllegalArgumentException("Product ID cannot be null or invalid.");
        }
    }

    private void validateCategory(Long categoryId) {
        if (categoryService.findById(categoryId) == null) {
            throw new IllegalArgumentException("Category ID cannot be null or invalid.");
        }
    }
}
