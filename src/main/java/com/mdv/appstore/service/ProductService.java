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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final FileService fileService;

    @Transactional
    public void createProduct(ProductRequest productRequest) throws IOException {
        String imageUrl = fileService.storeFile(productRequest.getImage());
        ProductCreateDTO productCreateDTO =
                ProductCreateDTO.builder()
                        .name(productRequest.getName())
                        .price(productRequest.getPrice())
                        .imageUrl(imageUrl)
                        .brandId(productRequest.getBrandId())
                        .build();
        productMapper.createProduct(productCreateDTO);
    }

    public List<ProductDTO> findAll() {
        return productMapper.findAll();
    }

    @Transactional
    public ProductDTO findById(Long id) {
        ProductDTO productDTO = getProductByIdOrThrow(id);
        productMapper.incrementViewCount(id);
        return productDTO;
    }

    private ProductDTO getProductByIdOrThrow(Long id) {
        ProductDTO productDTO = productMapper.findById(id);
        if (productDTO == null) {
            throw new DataNotFoundException("Product with id " + id + " not found");
        }
        return productDTO;
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
