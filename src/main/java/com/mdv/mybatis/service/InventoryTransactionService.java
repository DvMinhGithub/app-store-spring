package com.mdv.mybatis.service;

import com.mdv.mybatis.mapper.InventoryTransactionMapper;
import com.mdv.mybatis.model.dto.InventoryTransactionDTO;
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
