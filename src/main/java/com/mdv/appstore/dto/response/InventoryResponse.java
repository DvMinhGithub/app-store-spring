package com.mdv.appstore.dto.response;

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
public class InventoryResponse {
    Long id;
    Long productId;
    Double importPrice;
    Integer quantity;
    SupplierResponse supplier;
    String batchCode;
}
