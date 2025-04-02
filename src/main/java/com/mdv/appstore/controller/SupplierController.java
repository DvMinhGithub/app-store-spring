package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.request.SupplierRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.SupplierResponse;
import com.mdv.appstore.service.SupplierService;

@RestController
@RequestMapping("${app.api.base-url}/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid SupplierRequest supplier) {
        supplierService.create(supplier);
        return ApiResponse.success("Supplier created successfully");
    }

    @GetMapping
    public ApiResponse<List<SupplierResponse>> findAll() {
        return ApiResponse.success(supplierService.findAll(), "Suppliers retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<SupplierResponse> findById(@PathVariable Long id) {
        return ApiResponse.success(supplierService.findById(id), "Supplier retrieved successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id, @RequestBody @Valid SupplierRequest supplier) {
        supplierService.update(id, supplier);
        return ApiResponse.success("Supplier updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable Long id) {
        supplierService.deleteById(id);
        return ApiResponse.success("Supplier deleted successfully");
    }
}
