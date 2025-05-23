package com.mdv.appstore.dto.response;

import java.time.LocalDate;

import com.mdv.appstore.enums.DiscountType;

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
public class VoucherResponse {
    Long id;
    String code;
    DiscountType discountType;
    Double discountValue;
    Double minOrderAmount;
    LocalDate startDate;
    LocalDate endDate;
    Integer maxUses;
    Integer currentUses;
    Boolean isActive;
}
