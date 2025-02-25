package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.InventoryTransactionDTO;
import com.mdv.appstore.model.request.InventoryTransactionRequest;

@Mapper
public interface InventoryTransactionMapper {
    void importInventoryTransaction(@Param("it") InventoryTransactionRequest inventoryTransaction);

    void exportInventoryTransaction(@Param("it") InventoryTransactionRequest inventoryTransaction);

    List<InventoryTransactionDTO> getInventoryTransactions();

    List<InventoryTransactionDTO> findByProductId(@Param("productId") Long productId);

    List<InventoryTransactionDTO> findBySupplierId(@Param("supplierId") Long supplierId);
}
