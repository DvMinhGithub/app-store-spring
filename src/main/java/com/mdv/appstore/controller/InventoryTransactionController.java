package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.InventoryTransactionResponse;
import com.mdv.appstore.service.InventoryTransactionService;

@RestController
@RequestMapping("${app.api.base-url}/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {
    private final InventoryTransactionService inventoryTransactionService;

    @GetMapping
    public ApiResponse<List<InventoryTransactionResponse>> getInventoryTransactions() {
        return ApiResponse.success(
                inventoryTransactionService.getInventoryTransactions(),
                "Inventory transactions fetched successfully");
    }
}
