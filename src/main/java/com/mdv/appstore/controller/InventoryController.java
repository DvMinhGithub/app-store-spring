package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.InventoryRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.InventoryResponse;
import com.mdv.appstore.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ApiResponse<List<InventoryResponse>> getInventories() {
        return ApiResponse.success(inventoryService.getInventories(), "Inventories fetched successfully");
    }

    @PostMapping
    public ApiResponse<Void> importInventory(@RequestBody @Valid InventoryRequest inventoryRequest) {
        inventoryService.importInventory(inventoryRequest);
        return ApiResponse.success("Inventory created successfully");
    }

    @PostMapping("/export")
    public ApiResponse<Void> exportInventory(@RequestBody @Valid InventoryRequest inventoryRequest) {
        inventoryService.exportInventory(inventoryRequest);
        return ApiResponse.success("Inventory exported successfully");
    }
}
