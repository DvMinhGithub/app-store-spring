package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.BrandResponse;
import com.mdv.appstore.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ApiResponse<Object> createBrand(@RequestBody BrandRequest brand) {
        brandService.createBrand(brand);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Brand created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Brand fetched successfully")
                .data(brandService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable("id") Long id) {
        return ApiResponse.<BrandResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Brand fetched successfully")
                .data(brandService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateBrand(@PathVariable("id") Long id, @RequestBody BrandRequest brand) {
        brandService.updateBrand(id, brand);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Brand updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Brand deleted successfully")
                .build();
    }
}
