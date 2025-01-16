package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.dto.BrandDTO;
import com.mdv.mybatis.model.request.BrandRequest;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.servicer.BrandService;
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
@RequestMapping("/api/v1/brands")
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
