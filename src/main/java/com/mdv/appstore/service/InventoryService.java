package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.request.InventoryRequest;
import com.mdv.appstore.dto.request.InventoryTransactionRequest;
import com.mdv.appstore.dto.response.InventoryResponse;
import com.mdv.appstore.dto.response.ProductResponse;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.InventoryMapper;
import com.mdv.appstore.mapper.InventoryTransactionMapper;
import com.mdv.appstore.mapper.ProductMapper;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final ProductMapper productMapper;

    @Transactional
    public void importInventory(InventoryRequest inventory) {
        InventoryResponse inventoryResponse = inventoryMapper.findByBatchCode(inventory.getBatchCode());
        if (inventoryResponse != null) {
            throw new DuplicateEntryException("Batch code already exists");
        }
        ProductResponse product = productMapper.findById(inventory.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        Long totalQuantity = product.getTotalQuantity() + inventory.getQuantity();
        productMapper.updateTotalQuantity(inventory.getProductId(), totalQuantity);
        InventoryTransactionRequest inventoryTransaction =
                InventoryTransactionRequest.builder()
                        .productId(inventory.getProductId())
                        .quantity(inventory.getQuantity())
                        .type("IMPORT")
                        .supplierId(inventory.getSupplierId())
                        .build();
        inventoryTransactionMapper.importInventoryTransaction(inventoryTransaction);
        inventoryMapper.importInventory(inventory);
    }

    @Transactional
    public void exportInventory(InventoryRequest inventory) {
        ProductResponse product = productMapper.findById(inventory.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (product.getTotalQuantity() < inventory.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity");
        }
        if (inventoryMapper.findByBatchCode(inventory.getBatchCode()) == null) {
            throw new IllegalArgumentException("Batch code not found");
        }
        productMapper.updateTotalQuantity(
                inventory.getProductId(), product.getTotalQuantity() - inventory.getQuantity());
        InventoryTransactionRequest inventoryTransaction =
                InventoryTransactionRequest.builder()
                        .productId(inventory.getProductId())
                        .quantity(inventory.getQuantity())
                        .type("EXPORT")
                        .supplierId(inventory.getSupplierId())
                        .build();
        inventoryTransactionMapper.exportInventoryTransaction(inventoryTransaction);
        inventoryMapper.exportInventory(inventory);
    }

    public List<InventoryResponse> getInventories() {
        return inventoryMapper.getInventories();
    }

    public InventoryResponse findByBatchCode(String batchCode) {
        InventoryResponse inventory = inventoryMapper.findByBatchCode(batchCode);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found");
        }
        return inventory;
    }
}
