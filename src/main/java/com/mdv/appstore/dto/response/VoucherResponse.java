package com.mdv.appstore.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    Double conditionValue;
    Double discountPrice;
    LocalDate endTime;
    LocalDate startTime;
    Integer totalQuantity;
    Integer usedQuantity;

    @JsonProperty("isActive")
    Boolean isActive;
}
