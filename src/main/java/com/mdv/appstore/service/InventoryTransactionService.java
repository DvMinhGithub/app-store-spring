package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.response.InventoryTransactionResponse;
import com.mdv.appstore.mapper.InventoryTransactionMapper;

@Service
@RequiredArgsConstructor
public class InventoryTransactionService {
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public List<InventoryTransactionResponse> getInventoryTransactions() {
        return inventoryTransactionMapper.getInventoryTransactions();
    }
}
