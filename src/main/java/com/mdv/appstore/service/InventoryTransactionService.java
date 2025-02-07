package com.mdv.appstore.service;

import com.mdv.appstore.mapper.InventoryTransactionMapper;
import com.mdv.appstore.model.dto.InventoryTransactionDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryTransactionService {
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public List<InventoryTransactionDTO> getInventoryTransactions() {
        return inventoryTransactionMapper.getInventoryTransactions();
    }
}
