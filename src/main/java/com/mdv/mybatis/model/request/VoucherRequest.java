package com.mdv.mybatis.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class VoucherRequest {
    String code;
    BigDecimal conditionValue;
    BigDecimal discountPrice;
    LocalDate endTime;
    LocalDate startTime;
    Integer totalQuantity;
    Integer usedQuantity;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isActive;
}
