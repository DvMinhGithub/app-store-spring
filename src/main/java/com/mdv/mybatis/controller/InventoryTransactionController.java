package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.dto.InventoryTransactionDTO;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.service.InventoryTransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {
    private final InventoryTransactionService inventoryTransactionService;

    @GetMapping
    public ApiResponse<List<InventoryTransactionDTO>> getInventoryTransactions() {
        return ApiResponse.success(
                inventoryTransactionService.getInventoryTransactions(),
                "Inventory transactions fetched successfully");
    }
}
