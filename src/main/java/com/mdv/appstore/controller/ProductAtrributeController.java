package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.ProductAttributeDTO;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.ProductAtrributeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-attribute")
@RequiredArgsConstructor
public class ProductAtrributeController {
    private final ProductAtrributeService productAtrributeService;

    @GetMapping
    public ApiResponse<List<ProductAttributeDTO>> findAll() {
        return ApiResponse.<List<ProductAttributeDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute fetched successfully")
                .data(productAtrributeService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductAttributeDTO> findById(@PathVariable("id") Long id) {
        return ApiResponse.<ProductAttributeDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute fetched successfully")
                .data(productAtrributeService.findById(id))
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductAttributeDTO>> findAttributesByProductId(
            @PathVariable("productId") Long productId) {
        return ApiResponse.<List<ProductAttributeDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute fetched successfully")
                .data(productAtrributeService.findAttributesByProductId(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<Object> createProductAttribute(
            @RequestBody @Valid ProductAttributeDTO productAttribute) {
        productAtrributeService.createProductAtrribute(productAttribute);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Product attribute created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateProductAttribute(
            @PathVariable("id") Long id, @RequestBody @Valid ProductAttributeDTO productAttribute) {
        productAtrributeService.updateProductAtrribute(id, productAttribute);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProductAttribute(@PathVariable("id") Long id) {
        productAtrributeService.deleteProductAtrribute(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product attribute deleted successfully")
                .build();
    }
}
