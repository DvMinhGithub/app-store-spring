package com.mdv.mybatis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class VoucherDTO {
    Long id;
    String code;
    BigDecimal conditionValue;
    BigDecimal discountPrice;
    LocalDate endTime;
    LocalDate startTime;
    Integer totalQuantity;
    Integer usedQuantity;

    @JsonProperty("isActive")
    Boolean isActive;
}
