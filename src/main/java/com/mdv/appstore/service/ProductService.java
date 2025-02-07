package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductMapper;
import com.mdv.appstore.model.dto.ProductCreateDTO;
import com.mdv.appstore.model.dto.ProductDTO;
import com.mdv.appstore.model.request.ProductRequest;
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
        if (product == null || product.isDeleted()) {
            throw new DataNotFoundException("Product not found");
        }
        return product;
    }

    public void updateProduct(Long id, ProductRequest product) {
        findById(id);
        productMapper.updateProduct(id, product);
    }

    public void deleteProduct(Long id) {
        findById(id);
        productMapper.softDelete(id);
    }

    public void restoreProduct(Long id) {
        findById(id);
        productMapper.restore(id);
    }
}
