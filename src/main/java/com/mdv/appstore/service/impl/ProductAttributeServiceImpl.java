package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.response.ProductAttributeResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductAttributeMapper;
import com.mdv.appstore.service.ProductAttributeService;
import com.mdv.appstore.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService  {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductService productService;

    private static final String PRODUCT_ATRIBUTE_NOT_FOUND = "ProductAttribute not found";

    public void createProductAttribute(ProductAttributeResponse productAttribute) {
        productService.findById(productAttribute.getProductId());
        productAttributeMapper.create(productAttribute);
    }

    public List<ProductAttributeResponse> findAll() {
        return productAttributeMapper.findAll();
    }

    public ProductAttributeResponse findById(Long id) {
        ProductAttributeResponse productAttribute = productAttributeMapper.findById(id);
        if (productAttribute == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        return productAttribute;
    }

    public List<ProductAttributeResponse> findAttributesByProductId(Long productId) {
        productService.findById(productId);
        return productAttributeMapper.findAttributesByProductId(productId);
    }

    public void updateProductAttribute(Long id, ProductAttributeResponse productAttribute) {
        productService.findById(productAttribute.getProductId());
        if (productAttributeMapper.findById(id) == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        productAttributeMapper.update(id, productAttribute);
    }

    public void deleteProductAttribute(Long id) {
        if (productAttributeMapper.findById(id) == null) {
            throw new DataNotFoundException(PRODUCT_ATRIBUTE_NOT_FOUND);
        }
        productAttributeMapper.delete(id);
    }
}
