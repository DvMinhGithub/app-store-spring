package com.mdv.appstore.service;

import java.io.IOException;
import java.util.List;

import com.mdv.appstore.dto.request.ProductRequest;
import com.mdv.appstore.dto.response.ProductResponse;

public interface ProductService {

    void createProduct(ProductRequest productRequest) throws IOException;

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    void updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);

    void restoreProduct(Long id);
}
