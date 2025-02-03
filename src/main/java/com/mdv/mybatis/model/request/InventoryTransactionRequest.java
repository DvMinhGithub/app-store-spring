package com.mdv.mybatis.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InventoryTransactionRequest {
    Long productId;
    Integer quantity;
    String type;
    Long supplierId;
}
