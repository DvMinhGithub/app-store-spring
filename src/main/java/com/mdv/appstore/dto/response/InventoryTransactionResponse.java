package com.mdv.appstore.dto.response;

import java.time.LocalDateTime;

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
public class InventoryTransactionResponse {
    Long id;
    Long productId;
    SupplierResponse supplier;
    Integer quantity;
    String type;
    LocalDateTime transactionDate;
}
