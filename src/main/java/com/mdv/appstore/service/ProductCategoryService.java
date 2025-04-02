package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.response.CategoryResponse;
import com.mdv.appstore.dto.response.ProductResponse;

public interface ProductCategoryService {

    void create(Long productId, Long categoryId);

    List<CategoryResponse> getCategoriesByProductId(Long productId);

    List<ProductResponse> getProductsByCategoryId(Long categoryId);

    void delete(Long productId, Long categoryId);
}
