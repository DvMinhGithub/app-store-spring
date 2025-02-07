package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.ProductAtrributeMapper;
import com.mdv.appstore.model.dto.ProductAttributeDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAtrributeService {
    private final ProductAtrributeMapper productAtrributeMapper;
    private final ProductService productService;

    public void createProductAtrribute(ProductAttributeDTO productAtrribute) {
        productService.findById(productAtrribute.getProductId());
        productAtrributeMapper.create(productAtrribute);
    }

    public List<ProductAttributeDTO> findAll() {
        return productAtrributeMapper.findAll();
    }

    public ProductAttributeDTO findById(Long id) {
        ProductAttributeDTO productAtrribute = productAtrributeMapper.findById(id);
        if (productAtrribute == null) {
            throw new DataNotFoundException("ProductAtrribute not found");
        }
        return productAtrribute;
    }

    public List<ProductAttributeDTO> findAttributesByProductId(Long productId) {
        productService.findById(productId);
        return productAtrributeMapper.findAttributesByProductId(productId);
    }

    public void updateProductAtrribute(Long id, ProductAttributeDTO productAtrribute) {
        productService.findById(productAtrribute.getProductId());
        if (productAtrributeMapper.findById(id) == null) {
            throw new DataNotFoundException("ProductAtrribute not found");
        }
        productAtrributeMapper.update(id, productAtrribute);
    }

    public void deleteProductAtrribute(Long id) {
        if (productAtrributeMapper.findById(id) == null) {
            throw new DataNotFoundException("ProductAtrribute not found");
        }
        productAtrributeMapper.delete(id);
    }
}
