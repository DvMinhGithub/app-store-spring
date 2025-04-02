package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.response.ProductAttributeResponse;

public interface ProductAttributeService {

    void createProductAttribute(ProductAttributeResponse productAttribute);

    List<ProductAttributeResponse> findAll();

    ProductAttributeResponse findById(Long id);

    List<ProductAttributeResponse> findAttributesByProductId(Long productId);

    void updateProductAttribute(Long id, ProductAttributeResponse productAttribute);

    void deleteProductAttribute(Long id);
}
