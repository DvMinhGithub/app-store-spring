package com.mdv.appstore.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.appstore.dto.request.ProductRequest;
import com.mdv.appstore.dto.response.ProductCreateResponse;
import com.mdv.appstore.dto.response.ProductResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductMapper;
import com.mdv.appstore.service.FileService;
import com.mdv.appstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final FileService fileService;

    @Transactional
    public void createProduct(ProductRequest productRequest) throws IOException {
        String imageUrl = fileService.storeFile(productRequest.getImage());
        ProductCreateResponse productCreateResponse = ProductCreateResponse.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .imageUrl(imageUrl)
                .brandId(productRequest.getBrandId())
                .build();
        productMapper.createProduct(productCreateResponse);
    }

    public List<ProductResponse> findAll() {
        return productMapper.findAll();
    }

    @Transactional
    public ProductResponse findById(Long id) {
        ProductResponse productResponse = getProductByIdOrThrow(id);
        productMapper.incrementViewCount(id);
        return productResponse;
    }

    private ProductResponse getProductByIdOrThrow(Long id) {
        ProductResponse productResponse = productMapper.findById(id);
        if (productResponse == null) {
            throw new DataNotFoundException("Product with id " + id + " not found");
        }
        return productResponse;
    }

    @Transactional
    public void updateProduct(Long id, ProductRequest productRequest) {
        getProductByIdOrThrow(id);
        productMapper.updateProduct(id, productRequest);
    }

    @Transactional
    public void deleteProduct(Long id) {
        getProductByIdOrThrow(id);
        productMapper.softDelete(id);
    }

    @Transactional
    public void restoreProduct(Long id) {
        getProductByIdOrThrow(id);
        productMapper.restore(id);
    }
}
