package com.mdv.mybatis.mapper;

import com.mdv.mybatis.model.dto.InventoryDTO;
import com.mdv.mybatis.model.request.InventoryRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InventoryMapper {
    void importInventory(@Param("i") InventoryRequest inventory);

    void exportInventory(@Param("i") InventoryRequest inventory);

    List<InventoryDTO> getInventories();

    List<InventoryDTO> findByProductId(@Param("productId") Long productId);

    InventoryDTO findByBatchCode(@Param("batchCode") String batchCode);
}
