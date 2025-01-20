package com.mdv.mybatis.service;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.ProductMapper;
import com.mdv.mybatis.model.dto.ProductCreateDTO;
import com.mdv.mybatis.model.dto.ProductDTO;
import com.mdv.mybatis.model.request.ProductRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final FileService fileService;

    public void createProduct(ProductRequest product) throws IOException {
        String imageUrl = fileService.storeFile(product.getImage());
        ProductCreateDTO productDto =
                ProductCreateDTO.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .imageUrl(imageUrl)
                        .brandId(product.getBrandId())
                        .build();
        productMapper.createProduct(productDto);
    }

    public List<ProductDTO> findAll() {
        return productMapper.findAll();
    }

    public ProductDTO findById(Long id) {
        ProductDTO product = productMapper.findById(id);
        if (product == null || product.hasDeleted()) {
            throw new DataNotFoundException("Product not found");
        }
        return product;
    }

    public void updateProduct(Long id, ProductRequest product) {
        productMapper.updateProduct(id, product);
    }

    public void deleteProduct(Long id) {
        productMapper.softDelete(id);
    }

    public void restoreProduct(Long id) {
        ProductDTO product = productMapper.findById(id);
        if (product == null || product.hasDeleted()) {
            throw new DataNotFoundException("Product not found");
        }

        productMapper.restore(id);
    }
}
