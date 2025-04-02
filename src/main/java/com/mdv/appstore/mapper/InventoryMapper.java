package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.InventoryRequest;
import com.mdv.appstore.dto.response.InventoryResponse;

@Mapper
public interface InventoryMapper {
    void importInventory(@Param("i") InventoryRequest inventory);

    void exportInventory(@Param("i") InventoryRequest inventory);

    List<InventoryResponse> getInventories();

    List<InventoryResponse> findByProductId(@Param("productId") Long productId);

    InventoryResponse findByBatchCode(@Param("batchCode") String batchCode);
}
