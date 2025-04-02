package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.InventoryRequest;
import com.mdv.appstore.dto.response.InventoryResponse;

public interface InventoryService {

    void importInventory(InventoryRequest inventory);

    void exportInventory(InventoryRequest inventory);

    List<InventoryResponse> getInventories();

    InventoryResponse findByBatchCode(String batchCode);
}
