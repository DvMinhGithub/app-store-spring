package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.InventoryTransactionRequest;
import com.mdv.appstore.dto.response.InventoryTransactionResponse;

@Mapper
public interface InventoryTransactionMapper {
    void importInventoryTransaction(@Param("it") InventoryTransactionRequest inventoryTransaction);

    void exportInventoryTransaction(@Param("it") InventoryTransactionRequest inventoryTransaction);

    List<InventoryTransactionResponse> getInventoryTransactions();

    List<InventoryTransactionResponse> findByProductId(@Param("productId") Long productId);

    List<InventoryTransactionResponse> findBySupplierId(@Param("supplierId") Long supplierId);
}
