package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.dto.InventoryDTO;
import com.mdv.appstore.model.request.InventoryRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.InventoryService;

@RestController
@RequestMapping("${app.api.base-url}/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ApiResponse<List<InventoryDTO>> getInventories() {
        return ApiResponse.success(
                inventoryService.getInventories(), "Inventories fetched successfully");
    }

    @PostMapping
    public ApiResponse<Void> importInventory(
            @RequestBody @Valid InventoryRequest inventoryRequest) {
        inventoryService.importInventory(inventoryRequest);
        return ApiResponse.success("Inventory created successfully");
    }

    @PostMapping("/export")
    public ApiResponse<Void> exportInventory(
            @RequestBody @Valid InventoryRequest inventoryRequest) {
        inventoryService.exportInventory(inventoryRequest);
        return ApiResponse.success("Inventory exported successfully");
    }
}
