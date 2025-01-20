package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.dto.ProductDTO;
import com.mdv.mybatis.model.request.ProductRequest;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.service.ProductService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Object> createProduct(@ModelAttribute @Valid ProductRequest product) throws IOException {
        productService.createProduct(product);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductDTO>> findAll() {
        return ApiResponse.<List<ProductDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Product fetched successfully")
                .data(productService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDTO> findById(@PathVariable("id") Long id) {
        return ApiResponse.<ProductDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Product fetched successfully")
                .data(productService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateProduct(
            @PathVariable("id") Long id, @RequestBody ProductRequest product) {
        productService.updateProduct(id, product);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .build();
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<Object> restoreProduct(@PathVariable("id") Long id) {
        productService.restoreProduct(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Product restored successfully")
                .build();
    }
}
