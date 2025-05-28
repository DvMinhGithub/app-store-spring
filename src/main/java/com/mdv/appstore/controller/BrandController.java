package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.BrandResponse;
import com.mdv.appstore.dto.response.PaginationResponse;
import com.mdv.appstore.service.BrandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private static final String BRAND_CREATED_SUCCESS = "Brand created successfully";
    private static final String BRAND_FETCHED_SUCCESS = "Brand fetched successfully";
    private static final String BRAND_UPDATED_SUCCESS = "Brand updated successfully";
    private static final String BRAND_DELETED_SUCCESS = "Brand deleted successfully (soft delete)";
    private static final String BRAND_RESTORED_SUCCESS = "Brand restored successfully";

    @PostMapping
    public ApiResponse<Void> createBrand(@RequestBody BrandRequest brand) {
        brandService.createBrand(brand);
        return ApiResponse.success(BRAND_CREATED_SUCCESS);
    }

    @GetMapping
    public ApiResponse<PaginationResponse<BrandResponse>> getAllBrands(@Valid PaginationRequest request) {
        return ApiResponse.success(brandService.findAll(request), BRAND_FETCHED_SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable("id") Long id) {
        return ApiResponse.success(brandService.findById(id), BRAND_FETCHED_SUCCESS);
    }

    @GetMapping("/search")
    public ApiResponse<List<BrandResponse>> searchBrands(@RequestParam("name") String name) {
        return ApiResponse.success(brandService.findByName(name), BRAND_FETCHED_SUCCESS);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateBrand(@PathVariable("id") Long id, @RequestBody BrandRequest brand) {
        brandService.updateBrand(id, brand);
        return ApiResponse.success(BRAND_UPDATED_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrand(@PathVariable("id") Long id) {
        brandService.softDeleteBrand(id);
        return ApiResponse.success(BRAND_DELETED_SUCCESS);
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<Void> restoreBrand(@PathVariable("id") Long id) {
        brandService.restoreBrand(id);
        return ApiResponse.success(BRAND_RESTORED_SUCCESS);
    }
}
