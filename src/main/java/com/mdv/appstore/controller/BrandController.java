package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.dto.BrandDTO;
import com.mdv.appstore.model.request.BrandRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.BrandService;

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
    public ApiResponse<List<BrandDTO>> getAllBrands() {
        return ApiResponse.<List<BrandDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("Brand fetched successfully")
                .data(brandService.findAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandDTO> getBrandById(@PathVariable("id") Long id) {
        return ApiResponse.<BrandDTO>builder()
                .code(HttpStatus.OK.value())
                .message("Brand fetched successfully")
                .data(brandService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateBrand(
            @PathVariable("id") Long id, @RequestBody BrandRequest brand) {
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
