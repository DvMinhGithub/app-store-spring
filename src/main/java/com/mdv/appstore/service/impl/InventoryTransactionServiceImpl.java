package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.appstore.dto.response.InventoryTransactionResponse;
import com.mdv.appstore.mapper.InventoryTransactionMapper;
import com.mdv.appstore.service.InventoryTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public List<InventoryTransactionResponse> getInventoryTransactions() {
        return inventoryTransactionMapper.getInventoryTransactions();
    }
}
