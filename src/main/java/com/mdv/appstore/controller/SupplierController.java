package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.SupplierDTO;
import com.mdv.appstore.model.request.SupplierRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.SupplierService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<List<SupplierDTO>> findAll() {
        return ApiResponse.success(supplierService.findAll(), "Suppliers retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<SupplierDTO> findById(@PathVariable Long id) {
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
