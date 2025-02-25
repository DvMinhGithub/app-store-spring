package com.mdv.appstore.model.dto;

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
public class InventoryTransactionDTO {
    Long id;
    Long productId;
    SupplierDTO supplier;
    Integer quantity;
    String type;
    LocalDateTime transactionDate;
}
