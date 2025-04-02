package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.response.ProductAttributeResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductAtrributeMapper;

@Service
@RequiredArgsConstructor
public class ProductAtrributeService {
    private final ProductAtrributeMapper productAtrributeMapper;
    private final ProductService productService;

    private static final String PRODUCT_ATRIBUTE_NOT_FOUND = "ProductAtrribute not found";

    public void createProductAtrribute(ProductAttributeResponse productAtrribute) {
        productService.findById(productAtrribute.getProductId());
        productAtrributeMapper.create(productAtrribute);
    }

    public List<ProductAttributeResponse> findAll() {
        return productAtrributeMapper.findAll();
    }

    public ProductAttributeResponse findById(Long id) {
        ProductAttributeResponse productAtrribute = productAtrributeMapper.findById(id);
        if (productAtrribute == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        return productAtrribute;
    }

    public List<ProductAttributeResponse> findAttributesByProductId(Long productId) {
        productService.findById(productId);
        return productAtrributeMapper.findAttributesByProductId(productId);
    }

    public void updateProductAtrribute(Long id, ProductAttributeResponse productAtrribute) {
        productService.findById(productAtrribute.getProductId());
        if (productAtrributeMapper.findById(id) == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        productAtrributeMapper.update(id, productAtrribute);
    }

    public void deleteProductAtrribute(Long id) {
        if (productAtrributeMapper.findById(id) == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        productAtrributeMapper.delete(id);
    }
}
