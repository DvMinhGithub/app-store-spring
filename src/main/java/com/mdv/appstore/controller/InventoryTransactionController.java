package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.InventoryTransactionDTO;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.InventoryTransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-url}/inventory-transactions")
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
