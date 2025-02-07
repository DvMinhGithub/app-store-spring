package com.mdv.appstore.service;

import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.InventoryMapper;
import com.mdv.appstore.mapper.InventoryTransactionMapper;
import com.mdv.appstore.mapper.ProductMapper;
import com.mdv.appstore.model.dto.InventoryDTO;
import com.mdv.appstore.model.dto.ProductDTO;
import com.mdv.appstore.model.request.InventoryRequest;
import com.mdv.appstore.model.request.InventoryTransactionRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final ProductMapper productMapper;

    @Transactional
    public void importInventory(InventoryRequest inventory) {
        InventoryDTO inventoryDTO = inventoryMapper.findByBatchCode(inventory.getBatchCode());
        if (inventoryDTO != null) {
            throw new DuplicateEntryException("Batch code already exists");
        }
        ProductDTO product = productMapper.findById(inventory.getProductId());
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
        ProductDTO product = productMapper.findById(inventory.getProductId());
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

    public List<InventoryDTO> getInventories() {
        return inventoryMapper.getInventories();
    }

    public InventoryDTO findByBatchCode(String batchCode) {
        InventoryDTO inventory = inventoryMapper.findByBatchCode(batchCode);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found");
        }
        return inventory;
    }
}
