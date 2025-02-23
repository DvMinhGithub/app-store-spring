package com.mdv.appstore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    Double conditionValue;
    Double discountPrice;
    LocalDate endTime;
    LocalDate startTime;
    Integer totalQuantity;
    Integer usedQuantity;

    @JsonProperty("isActive")
    Boolean isActive;
}
