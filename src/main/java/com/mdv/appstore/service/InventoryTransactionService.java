package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.response.InventoryTransactionResponse;

public interface InventoryTransactionService {

    List<InventoryTransactionResponse> getInventoryTransactions();
}
