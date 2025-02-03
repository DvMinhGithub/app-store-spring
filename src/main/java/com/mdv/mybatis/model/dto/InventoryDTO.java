package com.mdv.mybatis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InventoryDTO {
    Long id;
    Long productId;
    Double importPrice;
    Integer quantity;
    SupplierDTO supplier;
    String batchCode;
}
