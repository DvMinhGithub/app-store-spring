package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.mapper.InventoryTransactionMapper;
import com.mdv.appstore.model.dto.InventoryTransactionDTO;

@Service
@RequiredArgsConstructor
public class InventoryTransactionService {
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public List<InventoryTransactionDTO> getInventoryTransactions() {
        return inventoryTransactionMapper.getInventoryTransactions();
    }
}
